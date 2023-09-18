package dev.banik.xunit;

public class Assertions {
    static void assertExpression(boolean expression) {
        assertExpression(expression, null);
    }

    static void assertExpression(boolean expression, String message) {
        if (!expression) {
            final String exceptionMessage;
            if (message != null && !message.isEmpty()) {
                exceptionMessage = message;
            } else {
                exceptionMessage = "expected true for assertion expression, but received false";
            }
            throw new RuntimeException("Assertion error: " + exceptionMessage);
        }
    }
}