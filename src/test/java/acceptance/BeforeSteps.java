package acceptance;

import io.cucumber.java.Before;

public class BeforeSteps {
    @Before
    public void before()
    {
        TestController.resetInstance();
    }
}
