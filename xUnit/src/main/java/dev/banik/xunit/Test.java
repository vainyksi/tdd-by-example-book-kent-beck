package dev.banik.xunit;

public abstract class Test {
    public abstract TestResult run(TestResult testResult);

    protected abstract void tearDown();

    protected abstract void setUp();
}
