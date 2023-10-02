package dev.banik.xunit;

import java.util.LinkedList;

public class TestSuite extends TestCase{
    private LinkedList<TestCase> tests = new LinkedList<>();

    TestSuite() {
        super();
    }

    public void add(TestCase testCase) {
        tests.add(testCase);
    }

    @Override
    public TestResult run(TestResult testResult) {
        TestResult result = new TestResult();
        for (TestCase test : tests) {
            test.run(result);
        }
        return result;
    }
}
