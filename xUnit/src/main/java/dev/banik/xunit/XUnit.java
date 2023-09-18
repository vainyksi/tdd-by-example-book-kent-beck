package dev.banik.xunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class XUnit {
    // ~~Invoke test method~~
    // Invoke setUp first
    // Invoke tearDown afterward
    // Invoke tearDown even if the test method fails
    // Run multiple tests
    // Report collected results

    public static void main(String[] args) {
//        TestCase("testMethod").run();

        WasRun test = new WasRun("testMethod");
        assertExpression(!test.wasRun);
        test.run();
        assertExpression(test.wasRun, "The test case should run, but flag was '%s'", String.valueOf(test.wasRun));

    }

    private static void assertExpression(boolean expression, String... messages) {
        if (!expression) {
            String exceptionMessage;
            if (messages.length > 0) {
                String[] params = new String[messages.length - 1];
                System.arraycopy(messages, 1, params, 0, messages.length - 1);
                exceptionMessage = String.format(messages[0], params);
            } else {
                exceptionMessage = "expected true, but received false";
            }
            throw new RuntimeException("Assertion error: " + exceptionMessage);
        }
    }

    static class TestCase implements Runnable {

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
                method.invoke(this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class WasRun extends TestCase {

        public boolean wasRun = false;

        public WasRun(String methodName) {
            super(methodName);
        }

        public void testMethod() {
            wasRun = true;
        }

    }
}
