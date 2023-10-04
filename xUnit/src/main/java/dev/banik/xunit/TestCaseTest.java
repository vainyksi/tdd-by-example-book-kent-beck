package dev.banik.xunit;

public class TestCaseTest extends TestCase {
    private TestResult methodUnderTestResult;

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

        suite.add(new TestCaseTest("testTemplateMethod"));
        suite.add(new TestCaseTest("testResult"));
        suite.add(new TestCaseTest("testFailedResultFormatting")); // chapter 22
        suite.add(new TestCaseTest("testFailedResult")); // chapter 22
        suite.add(new TestCaseTest("testSuite")); // chapter 23
        suite.add(new TestCaseTest("testTearDownAfterFailing"));
        suite.add(new TestCaseTest("failingTestWithExceptionDetails"));
        TestResult suiteResult = suite.run(new TestResult());
        System.out.println("Test Suite results: " + suiteResult.summary());
    }

    @Override
    protected void setUp() {
        methodUnderTestResult = new TestResult();
    }

    public void failingTestWithExceptionDetails() {
        FailingTestCase test = new FailingTestCase("testMethod");
        methodUnderTestResult = test.run(methodUnderTestResult);
        try {
            Assertions.assertExpression(methodUnderTestResult.summary().contains("1 failed"),
                    "test should fail, but did not: " + methodUnderTestResult.summary());
            Assertions.assertExpression(methodUnderTestResult.getReason() instanceof RuntimeException,
                    "exception thrown should be Runtime exception, but was: " + methodUnderTestResult.getReason() +
                            "with message: " + methodUnderTestResult.getReason().getMessage());
            Assertions.assertExpression(methodUnderTestResult.getReason().getMessage().equals("Failing testable testMethod"),
                    "exception message should contain: \"Failing testable testMethod\", but contained: \""
                            + methodUnderTestResult.getReason().getMessage() + "\"");
        } catch (Exception e) {
            System.err.println("TEST FAILED: " + e.getMessage());
        }
    }

    void testSuite() {
        TestSuite suite = new TestSuite();
        suite.add(new WasRun("testMethod"));
        suite.add(new WasRun("testBrokenMethod"));
        methodUnderTestResult = suite.run(this.methodUnderTestResult);
        Assertions.assertExpression("2 run, 1 failed".equals(methodUnderTestResult.summary()), methodUnderTestResult.summary());
    }

    void testFailedResult() {
        FailingTestCase test = new FailingTestCase("testMethod");
        methodUnderTestResult = test.run(this.methodUnderTestResult);
        Assertions.assertExpression("1 run, 1 failed".equals(methodUnderTestResult.summary()));
    }

    void testFailedResultFormatting() {
        methodUnderTestResult.testStarted();
        methodUnderTestResult.testFailed();
        Assertions.assertExpression("1 run, 1 failed".equals(methodUnderTestResult.summary()));
    }

    void testResult() {
        WasRun test = new WasRun("testMethod");
        methodUnderTestResult = test.run(methodUnderTestResult);
        Assertions.assertExpression("1 run, 0 failed".equals(methodUnderTestResult.summary()));
    }

    void testTemplateMethod() {
        WasRun test = new WasRun("testMethod");

        Assertions.assertExpression(test.log.isEmpty());
        methodUnderTestResult = test.run(methodUnderTestResult);
        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));
    }

    void testTearDownAfterFailing() {
        WasRun test = new FailingTestCase("testMethod");

        Assertions.assertExpression(test.log.isEmpty());
        methodUnderTestResult = test.run(methodUnderTestResult);
        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));
    }

}
