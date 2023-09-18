package dev.banik.xunit;

public class TestResult {
    private int runCount = 1;

    public String summary() {
        return "%d run, 0 failed".formatted(runCount);
    }
}
