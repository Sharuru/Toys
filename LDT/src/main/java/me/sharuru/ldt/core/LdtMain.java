package me.sharuru.ldt.core;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.ldt.core.common.LdtConstants;
import me.sharuru.ldt.core.common.LogModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
@Component
public class LdtMain {

    private static final String LOG_REGEXP = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3})\\s+(\\w+)\\s+(\\w.*)-\\s+(\\w.*)$";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    List<LogModel> suites = new ArrayList<>(0);

    public void main() {
        // Readfile
        try {
            Stream<String> logLines = Files.lines(Path.of("D:\\Logs\\console-output-04.log"));
            logLines.forEach(line -> {
                if (isTargetLogLine(line)) {
                    log.debug("Found target log: {}", line);
                    doLogDiagnostics(line);
                }
            });

            suites.forEach(s -> {
                log.info("Suite: {}", s.toString());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isTargetLogLine(String rawText) {
        return rawText.contains(LdtConstants.KatalonPackageName.TEST_SUITE_EXECUTOR) || rawText.contains(LdtConstants.KatalonPackageName.TEST_CASE_EXECUTOR);
    }

    public LogModel currentLogModel = null;
    public LogModel currentSubLogModel = null;

    public void doLogDiagnostics(String line) {
        Pattern pattern = Pattern.compile(LOG_REGEXP);
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            //log.error("Shouldn't go into this condition: {}", line);
        } else {
            LocalDateTime logDateTime = LocalDateTime.parse(matcher.group(1), DATE_TIME_FORMATTER);
            String logPkgName = matcher.group(3).trim();
            String logStr = matcher.group(4);

            // judge log type
            if (LdtConstants.KatalonPackageName.TEST_SUITE_EXECUTOR.equals(logPkgName)) {
                if (logStr.startsWith(LdtConstants.LogKeyword.START)) {
                    LogModel model = new LogModel();
                    model.setRawText(line);
                    model.setLogText(logStr);
                    model.setType(LdtConstants.LogType.TEST_SUITE);
                    model.setTid(logStr.substring(logStr.indexOf(LdtConstants.LogKeyword.START) + LdtConstants.LogKeyword.START.length()).trim());
                    model.setStartedTime(logDateTime);
                    currentLogModel = model;
                    //suites.add(model);
                } else if (logStr.startsWith(LdtConstants.LogKeyword.END)) {
                    currentLogModel.setFinishedTime(logDateTime);
                    suites.add(currentLogModel);
                    currentLogModel = null;
                    //String tid = logStr.substring(logStr.indexOf(LdtConstants.LogKeyword.END) + LdtConstants.LogKeyword.END.length()).trim();
//                    suites.forEach(s -> {
//                        if (tid.equals(s.getTid())) {
//                            s.setFinishedTime(logDateTime);
//                        }
//                    });
                }
            } else if (LdtConstants.KatalonPackageName.TEST_CASE_EXECUTOR.equals(logPkgName)) {
                if (logStr.startsWith(LdtConstants.LogKeyword.START)) {
                    LogModel model = new LogModel();
                    model.setRawText(line);
                    model.setLogText(logStr);
                    model.setType(LdtConstants.LogType.TEST_CASE);
                    model.setTid(logStr.substring(logStr.indexOf(LdtConstants.LogKeyword.START) + LdtConstants.LogKeyword.START.length()).trim());
                    model.setStartedTime(logDateTime);
                    currentSubLogModel = model;
                }else if (logStr.startsWith(LdtConstants.LogKeyword.END) && !logStr.startsWith(LdtConstants.LogKeyword.END_CALL)) {
                    currentSubLogModel.setFinishedTime(logDateTime);
                    currentLogModel.getChildLogModels().add(currentSubLogModel);
                    currentSubLogModel = null;
                }
            }
        }
    }
}
