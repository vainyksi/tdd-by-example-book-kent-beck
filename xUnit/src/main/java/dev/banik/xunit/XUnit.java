package dev.banik.xunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class XUnit {
    public static void main(String[] args) {
//        TestCase("testMethod").run();

        WasRun test = new WasRun("testMethod");
        assert !test.wasRun;
        test.run();
        assert test.wasRun;

    }

    static class TestCase {

        protected final Method method;

        TestCase(String methodName) {
            try {
                this.method = this.getClass().getMethod(methodName);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class WasRun extends TestCase implements Runnable {

        public boolean wasRun = false;

        public WasRun(String methodName) {
            super(methodName);
        }

        public void testMethod() {
            wasRun = true;
        }

        @Override
        public void run() {
            try {
                method.invoke(this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
