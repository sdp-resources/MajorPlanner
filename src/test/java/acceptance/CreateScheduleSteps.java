package acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import io.cucumber.java.Before;
import majorPlanner.entity.Schedule;
import majorPlanner.response.Response;
import org.junit.Assert;

import java.util.List;

public class CreateScheduleSteps {

    @When("{word} creates a schedule called {string} with description {string} in the name of {word}")
    public void CreatesAScheduleInTheNameOf(String sessionUser, String scheduleName, String description, String ownerUser) {
         TestController.getInstance().createSchedule(sessionUser, ownerUser, scheduleName, description);
    }

    @Then("there exists a schedule called {string} with description {string} owned by {word}")
    public void ScheduleExists(String scheduleName, String description, String username) {
        List<Schedule> schedules = TestController.getInstance().gateway.getSchedulesFromOwnerId(username);;
        Assert.assertTrue(hasOneSchedule(schedules));
        Assert.assertEquals(scheduleName, getFirstSchedule(schedules).getName());
        Assert.assertEquals(description, getFirstSchedule(schedules).getDescription());
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
