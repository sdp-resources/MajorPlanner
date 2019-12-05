package acceptance;

import cucumber.api.java.en.Given;
import io.cucumber.java.en.When;
import json.JsonConverter;
import majorPlanner.entity.Program;
import majorPlanner.entity.Role;
import majorPlanner.entity.User;
import majorPlanner.session.Session;

import java.io.IOException;

public class CompareSteps {
    @Given("the schedule with id {word} has schedule:")
    public void userHasRole(String scheduleIdName, String programJson) throws IOException {
        Program p = new JsonConverter().deserializeProgram(programJson);
        TestController.getInstance().gateway.getSchedule(TestContext.getId(scheduleIdName)).addProgram(p);
    }

    @When("{word} compares the schedule with id {word}")
    public void uComparesTheScheduleWithIdS(String user, String scheduleIdName) {
        Session session = TestContext.getSession(user);
        int scheduleId = TestContext.getId(scheduleIdName);
        TestController.getInstance().compareSchedule(session, scheduleId);
    }
}
