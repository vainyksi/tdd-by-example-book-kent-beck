package dev.banik.xunit;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestCaseTest extends TestCase {
    private TestResult testCaseResult;

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
    //   ~~print success result of a suite~~
    //   ~~print failed result of a suite~~

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
        suite.add(new TestCaseTest("testSuitePrintsSuccessfulResult"));
        suite.add(new TestCaseTest("testSuitePrintsFailedResult"));
        TestResult suiteResult = suite.run(new TestResult());

        printSuiteResult(suiteResult);
    }

    private static void printSuiteResult(TestResult suiteResult) {
        if (suiteResult.isSuccess()) {
            System.out.println("Test Suite results: " + suiteResult.summary());
        } else {
            System.err.println("TEST SUITE FAILED: " + suiteResult.getReason().getMessage());
            System.err.println("TEST SUITE RESULTS: " + suiteResult.summary());
        }
    }

    @Override
    protected void setUp() {
        testCaseResult = new TestResult();
    }

    public void testSuitePrintsFailedResult() throws Throwable {
        TestSuite suite = new TestSuite();
        final AtomicBoolean wasRun = new AtomicBoolean(false);
        suite.add(new TestCase("testMethod") {
            public void testMethod() {
                throw new RuntimeException("testMethod failed");
            }
        });
        testCaseResult = suite.run(testCaseResult);
        TestSuitePrinter printer = new TestSuitePrinter();
        suite.print(testCaseResult, printer);
        Assertions.assertExpression(printer.log.equals("""
                        TEST SUITE FAILED: testMethod failed
                        TEST SUITE RESULTS: 1 run, 1 failed
                        """),
                "printer does not contain correct text");
    }

    public void testSuitePrintsSuccessfulResult() {
        TestSuite suite = new TestSuite();
        WasRun testCase = new WasRun("testMethod");
        suite.add(testCase);
        testCaseResult = suite.run(testCaseResult);
        TestSuitePrinter printer = new TestSuitePrinter();
        suite.print(testCaseResult, printer);
        Assertions.assertExpression(testCase.wasRun());
        Assertions.assertExpression(printer.log.equals("Test Suite results: 1 run, 0 failed\n"),
                "printer does not contain correct text");
    }

    public void testCatchSetupErrors() {
        WasRun testCaseToTest = new WasRun("testMethod") {
            @Override
            protected void setUp() {
                throw new RuntimeException("setUp error occurred");
            }

            public void testMethod() {
                super.testMethod();
            }
        };

        testCaseResult = testCaseToTest.run(new TestResult());
        Assertions.assertExpression(!testCaseToTest.wasRun());
        Assertions.assertExpression(testCaseResult.summary().contains("1 failed"));
        Assertions.assertExpression(testCaseResult.getReason() instanceof RuntimeException);
        Assertions.assertExpression(testCaseResult.getReason().getMessage().equals("setUp error occurred"));
    }

    public void failingTestWithExceptionDetails() {
        FailingTestCase test = new FailingTestCase("testMethod");
        testCaseResult = test.run(testCaseResult);
        Assertions.assertExpression(testCaseResult.summary().contains("1 failed"),
                "test should fail, but did not: " + testCaseResult.summary());
        Assertions.assertExpression(testCaseResult.getReason() instanceof RuntimeException,
                "exception thrown should be Runtime exception, but was: " + testCaseResult.getReason() +
                        "with message: " + testCaseResult.getReason().getMessage());
        Assertions.assertExpression(testCaseResult.getReason().getMessage().equals("Failing testable testMethod"),
                "exception message should contain: \"Failing testable testMethod\", but contained: \""
                        + testCaseResult.getReason().getMessage() + "\"");
    }

    void testSuite() {
        TestSuite suite = new TestSuite();
        suite.add(new WasRun("testMethod"));
        suite.add(new WasRun("testBrokenMethod"));
        testCaseResult = suite.run(testCaseResult);
        Assertions.assertExpression("2 run, 1 failed".equals(testCaseResult.summary()), testCaseResult.summary());
    }

    void testFailedResult() {
        FailingTestCase test = new FailingTestCase("testMethod");
        testCaseResult = test.run(testCaseResult);
        Assertions.assertExpression("1 run, 1 failed".equals(testCaseResult.summary()));
    }

    void testFailedResultFormatting() {
        testCaseResult.testStarted();
        testCaseResult.testFailed();
        Assertions.assertExpression("1 run, 1 failed".equals(testCaseResult.summary()));
    }

    void testResult() {
        WasRun test = new WasRun("testMethod");
        testCaseResult = test.run(testCaseResult);
        Assertions.assertExpression("1 run, 0 failed".equals(testCaseResult.summary()));
    }

    void testTemplateMethod() {
        WasRun test = new WasRun("testMethod");

        Assertions.assertExpression(test.log.isEmpty());
        testCaseResult = test.run(testCaseResult);
        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));
    }

    void testTearDownAfterFailing() {
        WasRun test = new FailingTestCase("testMethod");

        Assertions.assertExpression(test.log.isEmpty());
        testCaseResult = test.run(testCaseResult);
        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));
        Assertions.assertExpression(test.wasRun());
    }

}
