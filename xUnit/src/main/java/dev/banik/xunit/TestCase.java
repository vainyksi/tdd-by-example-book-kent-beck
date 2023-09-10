package dev.banik.xunit;

public class TestCase {
    public static void main(String[] args) {
//        TestCase("testMethod").run();

        WasRun test = new WasRun("testMethod");
        assert test.wasRun == false;
        test.testMethod();
        assert test.wasRun == true;

    }

    static class WasRun {

        public boolean wasRun = false;

        public WasRun(String methodName) {
        }

        public void testMethod() {
        }
    }
}
