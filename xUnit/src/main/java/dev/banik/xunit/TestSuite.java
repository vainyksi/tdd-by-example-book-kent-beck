package dev.banik.xunit;

import java.util.LinkedList;

public class TestSuite extends Test {
    private final LinkedList<TestCase> tests = new LinkedList<>();

    public void add(TestCase testCase) {
        tests.add(testCase);
    }

    @Override
    public TestResult run(TestResult testResult) {
        for (TestCase test : tests) {
            test.run(testResult);
        }
        return testResult;
    }

    @Override
    protected void tearDown() {
    }

    @Override
    protected void setUp() {
    }

    public void print(TestResult testCaseResult, TestSuitePrinter printer) {
        if (testCaseResult.summary().contains("0 failed")) { // TODO remove duplication: `.contains("0 failed")`
            printSuccess(printer, testCaseResult.summary());
        } else {
            printFailed(printer, testCaseResult);
        }
    }

    private void printFailed(TestSuitePrinter printer, TestResult result) {
        printer.println("TEST SUITE FAILED: " + result.getReason().getMessage());
        printer.println("TEST SUITE RESULTS: " + result.summary());
    }

    private void printSuccess(TestSuitePrinter printer, String summary) {
        printer.println("Test Suite results: " + summary);
    }
}
