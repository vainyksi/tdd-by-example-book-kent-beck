package dev.banik.xunit;

public class TestResult {
    private int runCount = 0;

    public String summary() {
        return "%d run, 0 failed".formatted(runCount);
    }

    public void testStarted() {
        this.runCount++;
    }
}
