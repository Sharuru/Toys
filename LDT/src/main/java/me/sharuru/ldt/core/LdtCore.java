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

    public List<MetaTaskInfo> diagnostic(String inputLogPath) {
        log.info("Diagnostic on log file: {} started.", inputLogPath);
        List<MetaTaskInfo> testSuites = new ArrayList<>(0);

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputLogPath))) {
            MetaTaskInfo testSuiteInfo = null;
            MetaTaskInfo testCaseInfo = null;
            String line;
            while ((line = reader.readLine()) != null) {
                // check if the log line is generated by Katalon executor
                if (isTargetLogLine(line)) {
                    log.debug("Found target log line: {}", line);
                    Pattern pattern = Pattern.compile(LOG_REGEXP);
                    Matcher matcher = pattern.matcher(line);
                    // not a standard log line, maybe the regexp is outdated
                    if (!matcher.matches()) {
                        log.info("Not standard log line found: {}", line);
                    } else {
                        // get details from the log line
                        LocalDateTime logDateTime = LocalDateTime.parse(matcher.group(1), DATE_TIME_FORMATTER);
                        String logPkgName = matcher.group(3).trim();
                        String logStr = matcher.group(4);

                        if (LdtConstants.KatalonPackageName.TEST_SUITE_EXECUTOR.equals(logPkgName)) {
                            // test suite started
                            if (logStr.startsWith(LdtConstants.LogKeyword.START)) {
                                testSuiteInfo = new MetaTaskInfo();
                                testSuiteInfo.setType(LdtConstants.LogType.TEST_SUITE);
                                testSuiteInfo.setLogText(logStr);
                                testSuiteInfo.setTaskIdentifier(logStr.substring(logStr.indexOf(LdtConstants.LogKeyword.START) + LdtConstants.LogKeyword.START.length() + 1));
                                testSuiteInfo.setStartedTime(logDateTime);
                                // test suite ended
                            } else if (logStr.startsWith(LdtConstants.LogKeyword.END) && testSuiteInfo != null) {
                                testSuiteInfo.setFinishedTime(logDateTime);
                                testSuites.add(testSuiteInfo);
                            }
                        } else if (LdtConstants.KatalonPackageName.TEST_CASE_EXECUTOR.equals(logPkgName)) {
                            // test case started
                            if (logStr.startsWith(LdtConstants.LogKeyword.START)) {
                                testCaseInfo = new MetaTaskInfo();
                                testCaseInfo.setLogText(logStr);
                                testCaseInfo.setType(LdtConstants.LogType.TEST_CASE);
                                testCaseInfo.setTaskIdentifier(logStr.substring(logStr.indexOf(LdtConstants.LogKeyword.START) + LdtConstants.LogKeyword.START.length()).trim());
                                testCaseInfo.setStartedTime(logDateTime);
                                // test case ended
                            } else if (logStr.startsWith(LdtConstants.LogKeyword.END) && !logStr.startsWith(LdtConstants.LogKeyword.END_CALL) && testSuiteInfo != null && testCaseInfo != null) {
                                testCaseInfo.setFinishedTime(logDateTime);
                                testSuiteInfo.getChildTasks().add(testCaseInfo);
                            }
                        }

                    }
                }
            }
        } catch (Exception ex) {
            log.error("Error happened, the detail is:");
            ex.printStackTrace();
        }
        log.info("Diagnostic finished.");
        return testSuites;
    }

    public void write(List<MetaTaskInfo> testSuites, String outputPath) {
        writeDataResult(testSuites, outputPath);
    }

    /**
     * Write data result to the disk
     *
     * @param testSuites test suites info
     * @param outputPath output path
     */
    private void writeDataResult(List<MetaTaskInfo> testSuites, String outputPath) {
        log.info("Writing the data results to: {}", outputPath);
        Path outputFilePath = Paths.get(outputPath);
        List<String> outputs = new ArrayList<>();
        outputs.add("indexKey,identifier,executionTime,calculatedExecutionTime");
        AtomicInteger suiteNo = new AtomicInteger(1);
        testSuites.forEach(suits -> {
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
        log.info("Write data result finished to: {}", outputFilePath);
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
