package dev.banik.xunit;

public class TestResult {
    private int runCount = 0;
    private int errorCount = 0;
    private Throwable failingReason;

    public String summary() {
        return "%d run, %d failed".formatted(runCount, errorCount);
    }

    public void testStarted() {
        this.runCount++;
    }

    public void testFailed() {
        this.errorCount++;
    }

    public void setReason(Throwable exception) {
        this.failingReason = exception;
    }

    public Throwable getReason() {
        return failingReason;
    }

    public boolean isSuccess() {
        return this.errorCount == 0;
    }
}
