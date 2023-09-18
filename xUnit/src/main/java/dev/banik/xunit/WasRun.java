package dev.banik.xunit;

class WasRun extends TestCase {

    public boolean wasRun = false;
    public boolean wasSetUp;

    public WasRun(String methodName) {
        super(methodName);
    }

    @Override
    protected void setUp() {
        this.wasSetUp = true;
    }

    public void testMethod() {
        wasRun = true;
    }

}
