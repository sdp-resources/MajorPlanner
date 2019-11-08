package acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import io.cucumber.java.Before;
import majorPlanner.entity.*;
import org.junit.Assert;

import static org.hamcrest.CoreMatchers.is;

public class AddCourseSteps {
    private Term term;
    private Year year;

    @Before
    public void before() {
        TestController.resetInstance();
    }

    @Given("{string} is a course with id {string}")
    public void defineCourse(String name, String id) {
        TestController.getInstance().defineCourse(name, id);
    }

    @When("{word} adds {string} to {word} for {word} of {word} year")
    public void userAddsCourseToSchedule(String user, String course, String scheduleName, String term, String year) {
        TestController.getInstance().addCourse(user, course, scheduleName, Term.valueOf(term), Year.valueOf(year));
    }

    @When("{word} adds {string} to schedule with id {int} for {word} of {word} year")
    public void userAddsCourseToSchedule(String user, String course, int scheduleId, String term, String year) {
        TestController.getInstance().addCourse(user, course, scheduleId, Term.valueOf(term).toString(), Year.valueOf(year).toString());
    }

    @Then("{word} has the course, {string} during {word} of {word} year")
    public void scheduleHasTheCourse(String schedule, String course, String term, String year) {
        Assert.assertThat(TestController.getInstance().scheduleHasCourse(schedule, course, term, year), is(true));
    }
}