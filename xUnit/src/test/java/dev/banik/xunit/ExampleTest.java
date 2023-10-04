package dev.banik.xunit;

public class ExampleTest {

    public static void main(String[] args) {
        TestSuite suite = new TestSuite();

        suite.add(new TestCaseTest("testTest"));
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

    public void testTest() {
        String ahoj = "ahoj";
        Assertions.assertExpression(ahoj.equals("test"));
    }
}
