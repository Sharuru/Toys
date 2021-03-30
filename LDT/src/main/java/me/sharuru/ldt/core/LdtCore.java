package me.sharuru.ldt.core;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.ldt.core.common.LdtConstants;
import me.sharuru.ldt.core.common.MetaTaskInfo;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
// TODO
public class LdtCore {

    private static final String LOG_REGEXP = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3})\\s+(\\w+)\\s+([\\w\\.]+)\\s+-\\s+(.*)$";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public List<MetaTaskInfo> diagnostic(String inputLogPath) {
        List<MetaTaskInfo> testSuites = new ArrayList<>(0);

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputLogPath))) {
            MetaTaskInfo baseModel = null;
            MetaTaskInfo subModel = null;
            String line;
            while ((line = reader.readLine()) != null) {
                if (isTargetLogLine(line)) {
                    log.debug("Found target log: {}", line);
                    Pattern pattern = Pattern.compile(LOG_REGEXP);
                    Matcher matcher = pattern.matcher(line);
                    if (!matcher.matches()) {
                        log.error("Shouldn't go into this condition: {}", line);
                    } else {
                        // get details from line
                        LocalDateTime logDateTime = LocalDateTime.parse(matcher.group(1), DATE_TIME_FORMATTER);
                        String logPkgName = matcher.group(3).trim();
                        String logStr = matcher.group(4);

                        if (LdtConstants.KatalonPackageName.TEST_SUITE_EXECUTOR.equals(logPkgName)) {
                            if (logStr.startsWith(LdtConstants.LogKeyword.START)) {
                                baseModel = new MetaTaskInfo();
                                baseModel.setType(LdtConstants.LogType.TEST_SUITE);
                                baseModel.setLogText(logStr);
                                baseModel.setTaskIdentifier(logStr.substring(logStr.indexOf(LdtConstants.LogKeyword.START) + LdtConstants.LogKeyword.START.length() + 1));
                                baseModel.setStartedTime(logDateTime);
                            } else if (logStr.startsWith(LdtConstants.LogKeyword.END)) {
                                baseModel.setFinishedTime(logDateTime);
                                testSuites.add(baseModel);
                            }
                        } else if (LdtConstants.KatalonPackageName.TEST_CASE_EXECUTOR.equals(logPkgName)) {
                            if (logStr.startsWith(LdtConstants.LogKeyword.START)) {
                                subModel = new MetaTaskInfo();
                                subModel.setLogText(logStr);
                                subModel.setType(LdtConstants.LogType.TEST_CASE);
                                subModel.setTaskIdentifier(logStr.substring(logStr.indexOf(LdtConstants.LogKeyword.START) + LdtConstants.LogKeyword.START.length()).trim());
                                subModel.setStartedTime(logDateTime);
                            } else if (logStr.startsWith(LdtConstants.LogKeyword.END) && !logStr.startsWith(LdtConstants.LogKeyword.END_CALL)) {
                                subModel.setFinishedTime(logDateTime);
                                baseModel.getChildTasks().add(subModel);
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        testSuites.forEach(s -> {
            log.info("Get Suite: {}, time: {}s, sum: {}s", s.getTaskIdentifier(), s.getExecutionTime(), s.getTotalExecutionTime());
            s.getChildTasks().forEach(a -> {
                log.info("    With case: {}, time: {}s", a.getTaskIdentifier(), a.getExecutionTime());
            });
        });
        return testSuites;
    }

    private boolean isTargetLogLine(String rawText) {
        return rawText.contains(LdtConstants.KatalonPackageName.TEST_SUITE_EXECUTOR) || rawText.contains(LdtConstants.KatalonPackageName.TEST_CASE_EXECUTOR);
    }

}
