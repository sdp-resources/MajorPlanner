package acceptance;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.java.en.And;
import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;
import majorPlanner.session.Session;
import org.junit.Assert;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateScheduleSteps {

    @When("{word} creates a schedule called {string} with description {string} owned by {word}")
    public void createSchedule(String sessionUser, String scheduleName, String description, String ownerUser) {
        Session session = TestContext.getSession(sessionUser);
        TestController.getInstance().createSchedule(session, ownerUser, scheduleName, description);
    }

    @Then("there exists a schedule called {string} with description {string} owned by {word}")
    public void checkScheduleExists(String scheduleName, String description, String username) {
        List<Schedule> schedules = TestController.getInstance().gateway.getSchedulesFromOwnerId(username);
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

    @When("{word} creates a schedule with name {string} description {string} owned by {word} with resulting id {word}")
    public void createScheduleWithId(String sessionUser, String scheduleName, String description, String ownerKey, String id) {
        Session session = TestContext.getSession(sessionUser);
        User owner = TestContext.getUser(ownerKey);
        Response response = TestController.getInstance().createSchedule(session, owner.getUserID(), scheduleName, description);
        assertSuccessful(response);
        Schedule schedule = ((SuccessResponse<Schedule>) response).getValue();
        TestContext.put(id, schedule.getID());
    }

    @And("{word} views the schedule {word} with id {word}")
    public void viewScheduleWithId(String sessionName, String scheduleName, String idName) {
        int scheduleId = TestContext.getScheduleId(idName);
        Session session = TestContext.getSession(sessionName);
        Response response = TestController.getInstance().viewSchedule(session, scheduleId);
        assertSuccessful(response);
        Schedule schedule = ((SuccessResponse<Schedule>) response).getValue();
        TestContext.put(scheduleName, schedule);
    }

    @Then("the schedule {word} has name {string}")
    public void checkScheduleName(String key, String name) {
        Schedule schedule = TestContext.getSchedule(key);
        assertThat(schedule.getName(), is(name));
    }

    @Then("the schedule {word} has description {string}")
    public void checkScheduleDescription(String key, String name) {
        Schedule schedule = TestContext.getSchedule(key);
        assertThat(schedule.getDescription(), is(name));
    }
    @Then("the schedule {word} has no courses")
    public void checkScheduleHasNoCourses(String key) {
        Schedule schedule = TestContext.getSchedule(key);
        assertThat(schedule.isEmpty(), is(true));
    }
    private void assertSuccessful(Response response) {
        assertThat(response.containsError(), is(false));
    }

    @And("{word} is the scheduleId {int}")
    public void setIdNameToValue(String idName, int number) {
        TestContext.put(idName, number);
    }
}
