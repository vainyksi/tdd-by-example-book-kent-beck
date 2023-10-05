package dev.banik.xunit;

public class FailingTestCase extends WasRun {
    public FailingTestCase(String methodName) {
        super(methodName);
    }

    public void testMethod(){
        super.testMethod();
        throw new RuntimeException("Failing testable testMethod");
    }
}
