package dev.banik.xunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class TestCase {

    protected final Method method;

    TestCase(String methodName) {
        try {
            this.method = this.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public TestResult run() {
        try {
            this.setUp();
            method.invoke(this);
            this.tearDown();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return new TestResult() ;
    }

    protected void tearDown() {

    }

    protected void setUp() {
    }
}
