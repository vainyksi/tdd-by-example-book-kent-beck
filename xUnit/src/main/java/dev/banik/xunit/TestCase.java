package dev.banik.xunit;

import java.lang.reflect.Method;

public class TestCase extends Test {

    protected final Method method;

    TestCase(String methodName) {
        try {
            this.method = this.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TestResult run(TestResult testResult) {
        testResult.testStarted();
        try {
            this.setUp();
            method.invoke(this);
        } catch (Exception e) {
            testResult.testFailed();
            testResult.setReason((e.getCause() == null) ? e : e.getCause());
        }
        this.tearDown();

        return testResult;
    }

    @Override
    protected void tearDown() {
    }

    @Override
    protected void setUp() {
    }
}
