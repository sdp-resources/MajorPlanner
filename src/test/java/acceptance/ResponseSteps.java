package acceptance;

import cucumber.api.java.en.Then;
import majorPlanner.response.Response;
import org.junit.Assert;

public class ResponseSteps {
    @Then("the latest response has no error")
    public void theResponseHasNoError() {
        Assert.assertFalse(getLatestResponse().containsError());
    }

    @Then("the latest response has an error")
    public void theResponseHasAnError() {
        Assert.assertTrue(getLatestResponse().containsError());
    }

    private Response getLatestResponse()
    {
        return TestController.getInstance().responses.get(TestController.getInstance().responses.size() - 1);
    }
}
