package dev.banik.xunit;

public class TestCaseTest {
    // ~~Invoke test method~~
    // ~~Invoke setUp first~~
    // ~~Invoke tearDown afterward~~
    // Invoke tearDown even if the test method fails
    // Run multiple tests
    // ~~Report collected results~~
    // ~~Log string in WasRun~~
    // ~~Report failed tests~~
    // Catch and report setUp errors

    // TestCaseTest
    public static void main(String[] args) {
//        TestCase("testMethod").run();

        testTemplateMethod();
        testResult();
        testFailedResultFormatting(); // chapter 22
        testFailedResult(); // chapter 22

    }

    private static void testFailedResult() {
        FailingTestCase test = new FailingTestCase("testMethod");
        TestResult result = test.run();
        Assertions.assertExpression("1 run, 1 failed".equals(result.summary()));
    }

    private static void testFailedResultFormatting() {
        TestResult result = new TestResult();
        result.testStarted();
        result.testFailed();
        Assertions.assertExpression("1 run, 1 failed".equals(result.summary()));
    }

    private static void testResult() {
        WasRun test = new WasRun("testMethod");
        TestResult result = test.run();
        Assertions.assertExpression("1 run, 0 failed".equals(result.summary()));
    }

    private static void testTemplateMethod() {
        WasRun test = new WasRun("testMethod");

        Assertions.assertExpression(!test.wasRun);
        test.run();
        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));
    }

}
