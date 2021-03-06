package me.sharuru.ldt.core;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.ldt.core.common.LdtConstants;
import me.sharuru.ldt.core.common.MetaTaskInfo;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LDT core logic
 */
@Slf4j
@Component
public class LdtCore {

    private static final String LOG_REGEXP = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3})\\s+(\\w+)\\s+([\\w\\.]+)\\s+-\\s+(.*)$";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Diagnostic
     *
     * @param inputPaths input file path
     * @return diagnostic result
     */
    public List<MetaTaskInfo> diagnostic(List<String> inputPaths) {
        List<MetaTaskInfo> testSuites = new ArrayList<>(0);
        MetaTaskInfo testSuiteInfo = null;
        MetaTaskInfo testCaseInfo = null;

        for (String inputPath : inputPaths) {
            long logLineNo = 0L;
            log.info("Diagnostic on log file: {} started", inputPath);
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logLineNo++;
                    // check if the log line is generated by Katalon executor
                    if (isTargetLogLine(line)) {
                        log.debug("Found target log line: {}", line);
                        Pattern pattern = Pattern.compile(LOG_REGEXP);
                        Matcher matcher = pattern.matcher(line);
                        // not a standard log line, maybe the regexp is outdated
                        if (!matcher.matches()) {
                            log.warn("Not standard log line found: {}", line);
                        } else {
                            // get details from the log line
                            LocalDateTime logDateTime = LocalDateTime.parse(matcher.group(1), DATE_TIME_FORMATTER);
                            String logPkgName = matcher.group(3).trim();
                            String logStr = matcher.group(4);
                            // decide package
                            if (LdtConstants.KatalonPackageName.TEST_SUITE_EXECUTOR.equals(logPkgName)) {
                                // test suite started
                                if (logStr.startsWith(LdtConstants.LogKeyword.START)) {
                                    String taskIdentifier = getTaskIdentifier(logStr, LdtConstants.LogKeyword.START);
                                    if (testSuiteInfo != null && testSuiteInfo.getFinishedTime() == null) {
                                        log.warn("Broken test suite dropped(unfinished) {} from {} at line {}, replaced by: {} from {} at line {}",
                                                testSuiteInfo.getTaskIdentifier(), testSuiteInfo.getLogPath(), testSuiteInfo.getLogLineNo(),
                                                taskIdentifier, inputPath, logLineNo);
                                        testSuiteInfo.setFullyCompleted(false);
                                        testSuites.add(testSuiteInfo);
                                    }
                                    for (MetaTaskInfo testSuite : testSuites) {
                                        if (testSuite.getTaskIdentifier().equals(taskIdentifier) && testSuite.isFullyCompleted()) {
                                            log.warn("Broken test suite dropped(retried): {} from {} at line {}, retried as: {} from {} at line {}",
                                                    testSuite.getTaskIdentifier(), testSuite.getLogPath(), testSuite.getLogLineNo(),
                                                    taskIdentifier, inputPath, logLineNo);
                                            testSuite.setFullyCompleted(false);
                                        }
                                    }
                                    testSuiteInfo = new MetaTaskInfo();
                                    testSuiteInfo.setType(LdtConstants.LogType.TEST_SUITE);
                                    testSuiteInfo.setLogText(logStr);
                                    testSuiteInfo.setLogPath(inputPath);
                                    testSuiteInfo.setLogLineNo(logLineNo);
                                    testSuiteInfo.setTaskIdentifier(taskIdentifier);
                                    testSuiteInfo.setStartedTime(logDateTime);
                                    // test suite ended
                                } else if (logStr.startsWith(LdtConstants.LogKeyword.END)
                                        && testSuiteInfo != null
                                        && testSuiteInfo.getTaskIdentifier().equals(getTaskIdentifier(logStr, LdtConstants.LogKeyword.END))) {
                                    testSuiteInfo.setFinishedTime(logDateTime);
                                    testSuiteInfo.setFullyCompleted(true);
                                    testSuites.add(testSuiteInfo);
                                }
                            } else if (LdtConstants.KatalonPackageName.TEST_CASE_EXECUTOR.equals(logPkgName)) {
                                // test case started
                                if (logStr.startsWith(LdtConstants.LogKeyword.START)) {
                                    String taskIdentifier = getTaskIdentifier(logStr, LdtConstants.LogKeyword.START);
                                    if (testCaseInfo != null && testCaseInfo.getFinishedTime() == null) {
                                        log.warn("Broken test case dropped(unfinished): {} from {} at line {}, replaced by: {} from {} at line {}",
                                                testCaseInfo.getTaskIdentifier(), testCaseInfo.getLogPath(), testCaseInfo.getLogLineNo(),
                                                taskIdentifier, inputPath, logLineNo);
                                        testCaseInfo.setFullyCompleted(false);
                                        if (testSuiteInfo != null) {
                                            testSuiteInfo.getChildTasks().add(testSuiteInfo);
                                        }
                                    }
                                    testCaseInfo = new MetaTaskInfo();
                                    testCaseInfo.setLogText(logStr);
                                    testCaseInfo.setLogPath(inputPath);
                                    testCaseInfo.setLogLineNo(logLineNo);
                                    testCaseInfo.setType(LdtConstants.LogType.TEST_CASE);
                                    testCaseInfo.setTaskIdentifier(taskIdentifier);
                                    testCaseInfo.setStartedTime(logDateTime);
                                    // test case ended
                                } else if (logStr.startsWith(LdtConstants.LogKeyword.END)
                                        && !logStr.startsWith(LdtConstants.LogKeyword.END_CALL)
                                        && testSuiteInfo != null && testCaseInfo != null
                                        && testCaseInfo.getTaskIdentifier().equals(getTaskIdentifier(logStr, LdtConstants.LogKeyword.END))) {
                                    testCaseInfo.setFinishedTime(logDateTime);
                                    testSuiteInfo.getChildTasks().add(testCaseInfo);
                                    testCaseInfo.setFullyCompleted(true);
                                }
                            }

                        }
                    }
                }
            } catch (Exception ex) {
                log.error("Error happened, the detail is:");
                ex.printStackTrace();
            }
            log.info("Diagnostic on log file: {} finished", inputPath);
        }

        log.info("All diagnostic finished");
        return testSuites;
    }

    /**
     * Write results to the disk
     *
     * @param testSuites results data
     * @param outputPath output path
     */
    public void write(List<MetaTaskInfo> testSuites, String outputPath) {
        writeDataResult(testSuites, outputPath);
    }

    /**
     * Get task identifier
     *
     * @param logText log text
     * @param keyWord identifier keyword
     * @return task identifier
     */
    private String getTaskIdentifier(String logText, String keyWord) {
        return logText.substring(logText.indexOf(keyWord) + keyWord.length() + 1);
    }

    /**
     * Write data result to the disk
     *
     * @param testSuites test suites info
     * @param outputPath output path
     */
    private void writeDataResult(List<MetaTaskInfo> testSuites, String outputPath) {
        log.info("Writing the data results...");
        Path outputFilePath = Paths.get(outputPath);
        List<String> outputs = new ArrayList<>();
        outputs.add("indexKey,identifier,executionTime,calculatedExecutionTime");
        AtomicInteger suiteNo = new AtomicInteger(1);
        testSuites.forEach(suits -> {
            if (suits.isFullyCompleted()) {
                outputs.add("S" + String.format("%02d", suiteNo.get()) + "," + suits.getTaskIdentifier() + "," + suits.getExecutionTime() + "," + suits.getTotalExecutionTime());
                log.info("[    S{}] {}, Execution time: {}s, Calculated execution time: {}s.", String.format("%02d", suiteNo.get()), String.format("%1$-60s", suits.getTaskIdentifier()), suits.getExecutionTime(), suits.getTotalExecutionTime());
                AtomicInteger caseNo = new AtomicInteger(1);
                suits.getChildTasks().forEach(cases -> {
                    log.info("[S{}C{}] {}, Execution time: {}s.", String.format("%02d", suiteNo.get()), String.format("%03d", caseNo.get()), String.format("%1$-60s", cases.getTaskIdentifier()), cases.getExecutionTime());
                    outputs.add("S" + String.format("%02d", suiteNo.get()) + "C" + String.format("%03d", caseNo.get()) + "," + cases.getTaskIdentifier() + "," + cases.getExecutionTime() + "," + "-1");
                    caseNo.getAndIncrement();
                });
                suiteNo.getAndIncrement();
                caseNo.set(0);
            }

        });
        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {
            outputs.forEach(line -> {
                try {
                    writer.write(line + System.lineSeparator());
                } catch (IOException ex) {
                    log.error("Error happened, the detail is:");
                    ex.printStackTrace();
                }
            });
        } catch (IOException ex) {
            log.error("Error happened, the detail is:");
            ex.printStackTrace();
        }
        log.info("Finished writing data result to: {}", outputFilePath);
    }

    /**
     * Check if the log line is generated by the Katalon executor
     *
     * @param rawText log line
     * @return result
     */
    private boolean isTargetLogLine(String rawText) {
        return rawText.contains(LdtConstants.KatalonPackageName.TEST_SUITE_EXECUTOR) || rawText.contains(LdtConstants.KatalonPackageName.TEST_CASE_EXECUTOR);
    }

}
