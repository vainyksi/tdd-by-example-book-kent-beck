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
}
