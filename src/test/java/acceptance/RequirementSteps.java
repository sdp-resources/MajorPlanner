package acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import json.JsonConverter;
import majorPlanner.entity.StoredRequirement;
import majorPlanner.response.Response;
import org.junit.Assert;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.hasItem;
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
        response.handle((Object o) -> TestContext.put(courseListName, o), Assert::fail);
    }

    @Then("the list of courses {word} contains {string}")
    public void theListOfCoursesLContains(String courseListName, String courseName) {
        assertThat(TestContext.getCourseList(courseListName), hasItem(TestContext.getCourse(courseName)));
    }
}
