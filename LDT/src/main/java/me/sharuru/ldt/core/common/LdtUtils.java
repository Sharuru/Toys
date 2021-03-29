package me.sharuru.ldt.core.common;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LdtUtils {

//    private static final String LOG_REGEXP = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3})\\s+(\\w+)\\s+(\\w.*)-\\s+(\\w.*)$";
//    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//
//    public static boolean isTargetLogLine(String rawText) {
//        return rawText.contains(LdtConstants.KatalonPackageName.TEST_SUITE_EXECUTOR) || rawText.contains(LdtConstants.KatalonPackageName.TEST_CASE_EXECUTOR);
//    }
//        Pattern pattern = Pattern.compile(LOG_REGEXP);
//        Matcher matcher = pattern.matcher(rawText);
//        if (!matcher.matches()) {
//            log.debug("Not a Katalon log: {}", rawText);
//            return false;
//        } else {
//            String logDateStr = matcher.group(1);
//            String logStr = matcher.group(4);
////            String katalonPkgName = matcher.group(3).trim();
////            if (LdtConstants.KatalonPackageName.TEST_SUITE_EXECUTOR.equals(katalonPkgName)) {
////                if(logStr.startsWith(LdtConstants.LogType.TEST_SUITE_START)){
////                    return new LogModel(rawText, logStr, LdtConstants.LogType.TEST_SUITE_START, LocalDateTime.parse(logDateStr, DATE_TIME_FORMATTER));
////                }else if(logStr.startsWith(LdtConstants.LogType.TEST_SUITE_END)){
////                    return new LogModel(rawText, logStr, LdtConstants.LogType.TEST_SUITE_END, LocalDateTime.parse(logDateStr, DATE_TIME_FORMATTER));
////                }
////            } else if (LdtConstants.KatalonPackageName.TEST_CASE_EXECUTOR.equals(katalonPkgName)) {
////                if(logStr.startsWith(LdtConstants.LogType.))
////                //return new LogModel(rawText, matcher.group(4), LdtConstants.LogType.TEST_CASE, LocalDateTime.parse(logDateStr, DATE_TIME_FORMATTER));
////            } else {
////                log.debug("Not a target log: {}", rawText);
////                return null;
////            }
//        }
//    }
}