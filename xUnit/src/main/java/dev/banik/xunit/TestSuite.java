package dev.banik.xunit;

import java.util.LinkedList;

public class TestSuite {
    private LinkedList<TestCase> tests = new LinkedList<>();

    public void add(TestCase testCase) {
        tests.add(testCase);
    }

    public TestResult run() {
        TestResult result = new TestResult();
        for (TestCase test : tests) {
            test.run(result);
        }
        return result;
    }
}
