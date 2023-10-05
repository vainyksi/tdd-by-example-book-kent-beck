package dev.banik.xunit;

class WasRun extends TestCase {

    public String log = "";

    public WasRun(String methodName) {
        super(methodName);
    }

    @Override
    protected void setUp() {
        this.log = "setUp ";
    }

    @Override
    protected void tearDown() {
        this.log = this.log + "tearDown ";
    }

    public void testMethod() {
        this.log = this.log + "testMethod ";
    }

    public void testBrokenMethod() {
        throw new RuntimeException("Failing method");
    }

    public boolean wasRun() {
        return log.equals("setUp testMethod tearDown ");
    }
}
