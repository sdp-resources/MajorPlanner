package acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import json.JsonConverter;
import majorPlanner.entity.Course;
import majorPlanner.entity.Requirement;
import majorPlanner.entity.StoredRequirement;
import majorPlanner.response.Response;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

public class RequirementSteps {
    private JsonConverter converter = new JsonConverter();

    @Given("a requirement with id {word}")
    public void requirementJson(String idName, String requirementJson) throws IOException {
        StoredRequirement requirement = new StoredRequirement(converter.deserialize(requirementJson), "");
        TestController.getInstance().defineRequirement(requirement);
        TestContext.put(idName, requirement.getId());
    }

    @When("{word} views the course list {word} for the requirement with id {word}")
    public void viewCourseList(String userName, String courseListName, String requirementIdName) {
        Response response = TestController.getInstance().viewCourseList(TestContext.getSession(userName), TestContext.getId(requirementIdName));
    }

    @Then("course list {word} contains {string}")
    public void lContains(String courseListName, String course) {
        assertThat(TestContext.getCourseList(courseListName).contains(new Course(course)), is(true));
    }
}
