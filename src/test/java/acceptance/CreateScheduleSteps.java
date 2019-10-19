package acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import mock.MemoryGateway;
import mp.Controller;
import mp.entity.Schedule;
import mp.gateway.Gateway;
import mp.response.ErrorResponse;
import mp.response.Response;
import mp.session.Session;
import org.junit.Assert;

import java.util.List;

public class CreateScheduleSteps {
    private Controller controller;
    private Session session;
    private Gateway gateway;

    @Given("{word} is a {word} and logged in")
    public void HasRoleAndLoggedIn(String username, String role) {
        gateway = new MemoryGateway();
        controller = new Controller(gateway);
        session = new Session("y3w4d", username, role);
    }

    @When("{word} creates a schedule called {string}")
    public void CreateSchedule(String username, String scheduleName) {
        Response response = controller.createSchedule(session, username, scheduleName);
        Assert.assertFalse(response.containsError());
    }

    @Then("there exists a schedule called {string} owned by {word}")
    public void ScheduleExists(String scheduleName, String username) {
        List<Schedule> schedulesFromUsername = gateway.getSchedules(username);
        Assert.assertTrue(hasOneSchedule(schedulesFromUsername));
        Assert.assertTrue(firstScheduleHasName(schedulesFromUsername, scheduleName));
    }

    private boolean hasOneSchedule(List<Schedule> schedules)
    {
        return schedules.size() == 1;
    }

    private boolean firstScheduleHasName(List<Schedule> schedules, String name)
    {
        return schedules.get(0).getName() == name;
    }
}
