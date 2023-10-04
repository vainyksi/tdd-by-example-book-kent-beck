package dev.banik.xunit;

public class TestCaseTest extends TestCase {
    private TestResult result;

    TestCaseTest(String methodName) {
        super(methodName);
    }
    // ~~Invoke test method~~
    // ~~Invoke setUp first~~
    // ~~Invoke tearDown afterward~~
    // ~~Invoke tearDown even if the test method fails~~
    // ~~Run multiple tests~~
    // ~~Report collected results~~
    // ~~Log string in WasRun~~
    // ~~Report failed tests~~
    // Catch and report setUp errors

    // TestCaseTest
    public static void main(String[] args) {
//        TestCase("testMethod").run();

        TestSuite suite = new TestSuite();

        TestResult result = new TestResult();
        suite.add(new TestCaseTest("testTemplateMethod"));
        suite.add(new TestCaseTest("testResult"));
        suite.add(new TestCaseTest("testFailedResultFormatting")); // chapter 22
        suite.add(new TestCaseTest("testFailedResult")); // chapter 22
        suite.add(new TestCaseTest("testSuite")); // chapter 23
        result = suite.run(result);
        System.out.println(result.summary());

        System.out.println(new TestCaseTest("testTemplateMethod").run(new TestResult()).summary());
        System.out.println(new TestCaseTest("testResult").run(new TestResult()).summary());
        System.out.println(new TestCaseTest("testFailedResultFormatting").run(new TestResult()).summary());
        System.out.println(new TestCaseTest("testFailedResult").run(new TestResult()).summary());
        System.out.println(new TestCaseTest("testSuite").run(new TestResult()).summary());

        System.out.println(new TestCaseTest("testTearDownAfterFailing").run(new TestResult()).summary());

        System.out.println(new TestCaseTest("failingTestWithExceptionDetails").run(new TestResult()).summary());
    }

    @Override
    protected void setUp() {
        result = new TestResult();
    }

    public void failingTestWithExceptionDetails() {
        FailingTestCase test = new FailingTestCase("testMethod");
        TestResult testResult = test.run(new TestResult());
        try {
            Assertions.assertExpression(testResult.summary().contains("1 failed"),
                    "test should fail, but did not: " + testResult.summary());
            Assertions.assertExpression(testResult.getReason() instanceof RuntimeException,
                    "exception thrown should be Runtime exception, but was: " + testResult.getReason() +
                            "with message: " + testResult.getReason().getMessage());
            Assertions.assertExpression(testResult.getReason().getMessage().equals("Failing testable testMethod"),
                    "exception message should contain `Failing testable testMethod`, but contained "
                            + testResult.getReason().getMessage());
        } catch (Exception e) {
            System.err.println("TEST FAILED: " + e.getMessage());
        }
    }

    void testSuite() {
        TestSuite suite = new TestSuite();
        suite.add(new WasRun("testMethod"));
        suite.add(new WasRun("testBrokenMethod"));
        result = suite.run(this.result);
        Assertions.assertExpression("2 run, 1 failed".equals(result.summary()), result.summary());
    }

    void testFailedResult() {
        FailingTestCase test = new FailingTestCase("testMethod");
        result = test.run(this.result);
        Assertions.assertExpression("1 run, 1 failed".equals(result.summary()));
    }

    void testFailedResultFormatting() {
        result.testStarted();
        result.testFailed();
        Assertions.assertExpression("1 run, 1 failed".equals(result.summary()));
    }

    void testResult() {
        WasRun test = new WasRun("testMethod");
        result = test.run(result);
        Assertions.assertExpression("1 run, 0 failed".equals(result.summary()));
    }

    void testTemplateMethod() {
        WasRun test = new WasRun("testMethod");

        Assertions.assertExpression(test.log.isEmpty());
        result = test.run(result);
        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));
    }

    void testTearDownAfterFailing() {
        WasRun test = new FailingTestCase("testMethod");

        Assertions.assertExpression(test.log.isEmpty());
        result = test.run(result);
        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));
    }

}
