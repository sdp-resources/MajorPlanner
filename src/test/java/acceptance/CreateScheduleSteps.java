package acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import io.cucumber.java.Before;
import majorPlanner.entity.Schedule;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CreateScheduleSteps {
    @Before
    public void before()
    {
        TestController.resetInstance();
    }

    @Given("{word} is a {word}")
    public void UserHasRole(String name, String role)
    {
        TestController.getInstance().defineUser(name, role);
    }

    @Given("{word} is logged in")
    public void UserLoggedIn(String userId) {
        TestController.getInstance().defineSession(userId);
    }

    @When("{word} creates a schedule called {string} with description {string} in the name of {word}")
    public void CreatesAScheduleInTheNameOf(String sessionUser, String scheduleName, String description, String ownerUser) {
         TestController.getInstance().createSchedule(sessionUser, ownerUser, scheduleName, description);
    }

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

    @Then("there exists a schedule called {string} with description {string} owned by {word}")
    public void ScheduleExists(String scheduleName, String description, String username) {
        List<Schedule> schedules = TestController.getInstance().gateway.getSchedules(username);;
        Assert.assertTrue(hasOneSchedule(schedules));
        Assert.assertEquals(scheduleName, getFirstSchedule(schedules).getName());
        //Assert.assertEquals(description, getFirstSchedule(schedules).getDescription());
    }

    private boolean hasOneSchedule(List<Schedule> schedules)
    {
        return schedules.size() == 1;
    }

    private Schedule getFirstSchedule(List<Schedule> schedules)
    {
        return schedules.get(0);
    }
}
