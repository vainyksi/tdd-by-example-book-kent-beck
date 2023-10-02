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

    @Override
    protected void tearDown() {
        this.log = this.log + "tearDown ";
    }

    public void testMethod() {
        wasRun = true;
        this.log = this.log + "testMethod ";
    }

    public void testBrokenMethod() {
        throw new RuntimeException("Failing method");
    }

}
