package acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import io.cucumber.java.Before;
import majorPlanner.entity.Schedule;
import majorPlanner.entity.Term;
import majorPlanner.entity.Year;
import majorPlanner.response.Response;
import org.junit.Assert;

import java.util.List;

public class AddCourseSteps {
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
        TestController.getInstance().addCourse(user, course, scheduleId, Term.valueOf(term), Year.valueOf(year));
    }
}