package dev.banik.xunit;

public class XUnit {
    // ~~Invoke test method~~
    // ~~Invoke setUp first~~
    // Invoke tearDown afterward
    // Invoke tearDown even if the test method fails
    // Run multiple tests
    // Report collected results

    // TestCaseTest
    public static void main(String[] args) {
//        TestCase("testMethod").run();

        WasRun test = new WasRun("testMethod");
        assertExpression(!test.wasRun);
        test.run();
        assertExpression(test.wasRun, "The test case should run, but flag `wasRun` is '%s'".formatted(test.wasRun));
        assertExpression(test.wasSetUp);

    }

    private static void assertExpression(boolean expression) {
        assertExpression(expression, null);
    }

    private static void assertExpression(boolean expression, String message) {
        if (!expression) {
            final String exceptionMessage;
            if (message != null && !message.isEmpty()) {
                exceptionMessage = message;
            } else {
                exceptionMessage = "expected true for assertion expression, but received false";
            }
            throw new RuntimeException("Assertion error: " + exceptionMessage);
        }
    }

    static class WasRun extends TestCase {

        public boolean wasRun = false;
        public boolean wasSetUp;

        public WasRun(String methodName) {
            super(methodName);
        }

        @Override
        protected void setUp() {
            this.wasSetUp = true;
        }

        public void testMethod() {
            wasRun = true;
        }

    }
}
