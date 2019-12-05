package acceptance;

import cucumber.api.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import json.JsonConverter;
import majorPlanner.entity.*;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompareSteps {
    @Given("the schedule with id {word} has program:")
    public void userHasRole(String scheduleIdName, String programJson) throws IOException {
        Program p = new JsonConverter().deserializeProgram(programJson);
        TestController.getInstance().gateway.getSchedule(TestContext.getId(scheduleIdName)).addProgram(p);
    }

    @When("{word} compares the schedule with id {word} with resulting matches {word}")
    public void compareSchedule(String user, String scheduleIdName, String matchesName) {
        Session session = TestContext.getSession(user);
        int scheduleId = TestContext.getId(scheduleIdName);
        Response response = TestController.getInstance().compareSchedule(session, scheduleId);
        response.handle((Object o) -> TestContext.put(matchesName, o), Assert::fail);
    }

    @Then("matches {word} contains a requirement with description {string} and course {string}")
    public void matchesMContainsARequirementWithDescriptionAndCourse(String matchesName, String requirementDescription, String courseName) {
        List<MatchResult> matches = TestContext.getMatchList(matchesName);
        for(MatchResult mr : matches) {
            mr.handle((StoredRequirement req, Course course) -> {
                        assertThat(req.getDescription(), is(requirementDescription));
                        assertThat(course.equals(TestContext.getCourse(courseName)), is(true)); },
                    (StoredRequirement r) -> Assert.fail());
        }


    }
}
