package me.sharuru.ldt.core.common;


/**
 * Constants class
 */
public class LdtConstants {

    /**
     * Constants for the Katalon package name in the log text
     */
    public static final class KatalonPackageName {
        private static final String BASENAME = "c.k.katalon.core.main.";
        public static final String TEST_SUITE_EXECUTOR = BASENAME + "TestSuiteExecutor";
        public static final String TEST_CASE_EXECUTOR = BASENAME + "TestCaseExecutor";
    }

    /**
     * Constants for determined which kind of the log belonging to
     */
    public static final class LogType {
        public static final String TEST_SUITE = "TEST_SUITE";
        public static final String TEST_CASE = "TEST_CASE";
    }

    /**
     * Constants as well as keywords for matching the log text
     */
    public static final class LogKeyword {
        public static final String START = "START";
        public static final String END = "END";
        public static final String CALL = "CALL";
        public static final String END_CALL = "END CALL";
    }

}
