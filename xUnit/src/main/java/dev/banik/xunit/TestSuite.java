package dev.banik.xunit;

import java.util.LinkedList;

public class TestSuite extends TestCase{
    private LinkedList<TestCase> tests = new LinkedList<>();

    TestSuite() {
        super(null);
    }

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
}
