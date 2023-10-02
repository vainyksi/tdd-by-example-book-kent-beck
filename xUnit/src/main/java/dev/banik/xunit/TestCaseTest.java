package dev.banik.xunit;

public class TestCaseTest extends TestCase {
    TestCaseTest(String methodName) {
        super(methodName);
    }
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

        TestSuite suite = new TestSuite();

        TestResult result = new TestResult();
        suite.add(new TestCaseTest("testTemplateMethod"));
        suite.add(new TestCaseTest("testResult"));
        suite.add(new TestCaseTest("testFailedResultFormatting")); // chapter 22
        suite.add(new TestCaseTest("testFailedResult")); // chapter 22
        suite.add(new TestCaseTest("testSuite")); // chapter 23
        result = suite.run(result);

        System.out.println(result.summary());
    }

    TestResult testSuite() {
        TestSuite suite = new TestSuite();
        suite.add(new WasRun("testMethod"));
        suite.add(new WasRun("testBrokenMethod"));
        TestResult result = suite.run(new TestResult());
        Assertions.assertExpression("2 run, 1 failed".equals(result.summary()), result.summary());
        return result;
    }

    TestResult testFailedResult() {
        FailingTestCase test = new FailingTestCase("testMethod");
        TestResult result = new TestResult();
        result = test.run(result);
        Assertions.assertExpression("1 run, 1 failed".equals(result.summary()));
        return result;
    }

    TestResult testFailedResultFormatting() {
        TestResult result = new TestResult();
        result.testStarted();
        result.testFailed();
        Assertions.assertExpression("1 run, 1 failed".equals(result.summary()));
        return result;
    }

    TestResult testResult() {
        WasRun test = new WasRun("testMethod");
        TestResult result = new TestResult();
        result = test.run(result);
        Assertions.assertExpression("1 run, 0 failed".equals(result.summary()));
        return result;
    }

    TestResult testTemplateMethod() {
        WasRun test = new WasRun("testMethod");

        Assertions.assertExpression(!test.wasRun);
        TestResult result = new TestResult();
        result = test.run(result);
        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));
        return result;
    }

}
