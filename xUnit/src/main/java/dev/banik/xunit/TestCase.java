package dev.banik.xunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestCase {

    protected final Method method;

    TestCase(String methodName) {
        try {
            this.method = this.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public TestCase() {
        method = null;
    }

    public TestResult run(TestResult testResult) {
        testResult.testStarted();
        try {
            this.setUp();
            TestResult result = (TestResult) method.invoke(this);
            if (result != null) {
                testResult = result;
            }
            this.tearDown();
        } catch (IllegalAccessException | InvocationTargetException e) {
            testResult.testFailed();
        }

        return testResult;
    }

    protected void tearDown() {

    }

    protected void setUp() {
    }
}
