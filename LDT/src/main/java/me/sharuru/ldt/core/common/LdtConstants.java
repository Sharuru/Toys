package me.sharuru.ldt.core.common;

public class LdtConstants {

    public static final class KatalonPackageName {
        private static final String BASENAME = "c.k.katalon.core.main.";
        public static final String TEST_SUITE_EXECUTOR = BASENAME + "TestSuiteExecutor";
        public static final String TEST_CASE_EXECUTOR = BASENAME + "TestCaseExecutor";
    }

    public static final class LogType {
        public static final String TEST_SUITE = "TEST_SUITE";
        public static final String TEST_CASE = "TEST_CASE";
        public static final String TEST_SUITE_START = "TEST_SUITE_START";
        public static final String TEST_SUITE_END = "TEST_SUITE_END";
        public static final String TEST_CASE_START = "TEST_CASE_START";
        public static final String TEST_CASE_END = "TEST_CASE_END";
        public static final String TEST_CASE_CALL = "TEST_CASE_CALL";
        public static final String TEST_CASE_END_CALL = "TEST_CASE_END_CALL";
    }

    public static final class LogKeyword {
        public static final String START = "START";
        public static final String END = "END";
        public static final String CALL = "CALL";
        public static final String END_CALL = "END CALL";
    }

}
