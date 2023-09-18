package dev.banik.xunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class TestCase implements Runnable {

    protected final Method method;

    TestCase(String methodName) {
        try {
            this.method = this.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            this.setUp();
            method.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    protected void setUp() {
    }
}
