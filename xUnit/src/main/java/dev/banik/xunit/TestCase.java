package dev.banik.xunit;

public class TestCase {
    public static void main(String[] args) {
//        TestCase("testMethod").run();

        WasRun test = new WasRun("testMethod");
        assert !test.wasRun;
        test.testMethod();
        assert test.wasRun;

    }

    static class WasRun {

        public boolean wasRun = false;

        public WasRun(String methodName) {
        }

        public void testMethod() {
            wasRun = true;
        }
    }
}
