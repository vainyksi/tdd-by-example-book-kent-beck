package dev.banik.xunit;

public class XUnit {
    // ~~Invoke test method~~
    // ~~Invoke setUp first~~
    // Invoke tearDown afterward
    // Invoke tearDown even if the test method fails
    // Run multiple tests
    // Report collected results

    // TestCaseTest
    public static void main(String[] args) {
//        TestCase("testMethod").run();

        WasRun test = new WasRun("testMethod");

        Assertions.assertExpression(!test.wasRun);
        test.run();

        Assertions.assertExpression("setUp testMethod tearDown ".equals(test.log));

    }

}
