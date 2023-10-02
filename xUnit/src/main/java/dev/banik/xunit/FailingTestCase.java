package dev.banik.xunit;

public class FailingTestCase extends TestCase {
    public FailingTestCase(String methodName) {
        super(methodName);
    }

    public void testMethod(){
        throw new RuntimeException("Failing testable testMethod");
    }
}
