package dev.banik.xunit;

public class TestCaseTest {
    // ~~Invoke test method~~
    // ~~Invoke setUp first~~
    // ~~Invoke tearDown afterward~~
    // Invoke tearDown even if the test method fails
    // *Run multiple tests
    // ~~Report collected results~~
    // ~~Log string in WasRun~~
    // ~~Report failed tests~~
    // Catch and report setUp errors

    // TestCaseTest
    public static void main(String[] args) {
//        TestCase("testMethod").run();

        System.out.println(testTemplateMethod().summary());
        System.out.println(testResult().summary());
        System.out.println(testFailedResultFormatting().summary()); // chapter 22
        System.out.println(testFailedResult().summary()); // chapter 22
    }

    private static TestResult testFailedResult() {
        FailingTestCase test = new FailingTestCase("testMethod");
        TestResult result = test.run();
        Assertions.assertExpression("1 run, 1 failed".equals(result.summary()));
        return result;
    }

    private static TestResult testFailedResultFormatting() {
        TestResult result = new TestResult();
        result.testStarted();
        result.testFailed();
        Assertions.assertExpression("1 run, 1 failed".equals(result.summary()));
        return result;
    }

    private static TestResult testResult() {
        WasRun test = new WasRun("testMethod");
        TestResult result = test.run();
        Assertions.assertExpression("1 run, 0 failed".equals(result.summary()));
        return result;
    }

    private static TestResult testTemplateMethod() {
        WasRun test = new WasRun("testMethod");

        Assertions.assertExpression(!test.wasRun);
        TestResult result = test.run();
        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));
        return result;
    }

}
