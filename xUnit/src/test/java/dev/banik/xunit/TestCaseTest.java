package dev.banik.xunit;

import java.util.concurrent.atomic.AtomicBoolean;

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
    // ~~Catch and report setUp errors~~
    // Invoke tests by generics methods instead of string method names
    // Add global exception/error handling as part of an evaluation
    // Add exception/error handling, when it happens before the test execution (in run method) as part of an evaluation
    // Add test runner to run test cases - distinguish between running tests and testing the methods
    //   `main` & `printSuiteResult` in ExampleTest class are basically runner
    // Run test suit via test runner
    // Print all reasons for all the failed tests in a suite

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
        suite.add(new TestCaseTest("testCatchSetupErrors"));
        TestResult suiteResult = suite.run(new TestResult());

        printSuiteResult(suiteResult);
    }

    private static void printSuiteResult(TestResult suiteResult) {
        if (!suiteResult.summary().contains("0 failed")) { // TODO remove duplication: `.contains("0 failed")`
            System.err.println("TEST SUITE FAILED: " + suiteResult.getReason().getMessage());
            System.err.println("TEST SUITE RESULTS: " + suiteResult.summary());
        } else {
            System.out.println("Test Suite results: " + suiteResult.summary());
        }
    }

    @Override
    protected void setUp() {
        methodUnderTestResult = new TestResult();
    }

    public void testCatchSetupErrors() {
        final AtomicBoolean wasRun = new AtomicBoolean(false);
        TestCase testCaseToTest = new TestCase("testMethod") {

            @Override
            protected void setUp() {
                throw new RuntimeException("setUp error occurred");
            }

            public void testMethod() {
                wasRun.set(true);
            }
        };

        TestResult testCaseResult = testCaseToTest.run(new TestResult());
        Assertions.assertExpression(wasRun.get() == false);
        Assertions.assertExpression(testCaseResult.summary().contains("1 failed"));
        Assertions.assertExpression(testCaseResult.getReason() instanceof RuntimeException);
        Assertions.assertExpression(testCaseResult.getReason().getMessage().equals("setUp error occurred"));
    }

    public void failingTestWithExceptionDetails() {
        FailingTestCase test = new FailingTestCase("testMethod");
        methodUnderTestResult = test.run(methodUnderTestResult);
        Assertions.assertExpression(methodUnderTestResult.summary().contains("1 failed"),
                "test should fail, but did not: " + methodUnderTestResult.summary());
        Assertions.assertExpression(methodUnderTestResult.getReason() instanceof RuntimeException,
                "exception thrown should be Runtime exception, but was: " + methodUnderTestResult.getReason() +
                        "with message: " + methodUnderTestResult.getReason().getMessage());
        Assertions.assertExpression(methodUnderTestResult.getReason().getMessage().equals("Failing testable testMethod"),
                "exception message should contain: \"Failing testable testMethod\", but contained: \""
                        + methodUnderTestResult.getReason().getMessage() + "\"");
    }

    void testSuite() {
        TestSuite suite = new TestSuite();
        suite.add(new WasRun("testMethod"));
        suite.add(new WasRun("testBrokenMethod"));
        methodUnderTestResult = suite.run(methodUnderTestResult);
        Assertions.assertExpression("2 run, 1 failed".equals(methodUnderTestResult.summary()), methodUnderTestResult.summary());
    }

    void testFailedResult() {
        FailingTestCase test = new FailingTestCase("testMethod");
        methodUnderTestResult = test.run(methodUnderTestResult);
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
