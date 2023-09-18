package dev.banik.xunit;

class WasRun extends TestCase {

    public boolean wasRun = false;
    public String log = "";

    public WasRun(String methodName) {
        super(methodName);
    }

    @Override
    protected void setUp() {
        this.wasRun = false;
        this.log = "setUp ";
    }

    public void testMethod() {
        wasRun = true;
    }

}
