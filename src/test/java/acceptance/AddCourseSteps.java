package acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import majorPlanner.entity.*;
import majorPlanner.response.Response;
import org.junit.Assert;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class AddCourseSteps {
    private Period period;
    private Year year;

    @Before
    public void before() {
        TestController.resetInstance();
    }

    @Given("{string} is a course with id {string}")
    public void defineCourse(String name, String id) {
        Course course = new Course(id);
        TestController.getInstance().defineCourse(course);
        TestContext.put(name, course);
    }

    @Given("{string} is a course with id {string} with tags:")
    public void defineCourseWithTags(String name, String id, List<String> tags) {
        Course course = new Course(id);
        tags.forEach(course::addTag);
        TestController.getInstance().defineCourse(course);
        TestContext.put(name, course);
    }

    @When("{word} adds {string} to schedule with id {word} for {word} of {word} year")
    public void userAddsCourseToSchedule(String user, String courseId, String scheduleIDName, String period, String year) {
        Response response = TestController.getInstance().addCourse(TestContext.getSession(user), courseId, TestContext.getId(scheduleIDName), period, year);
    }

    @Then("{word} has the course, {string} during {word} of {word} year")
    public void scheduleHasTheCourse(String scheduleName, String courseName, String period, String year) {
        CalendarTerm term = new CalendarTerm(Period.valueOf(period), Year.valueOf(year));
        Schedule schedule = TestContext.getSchedule(scheduleName);
        Course course = TestContext.getCourse(courseName);
        Assert.assertThat(schedule.containsCourse(course, term), is(true));
    }

    @And("{word} has the course {word}")
    public void sHasTheCourseC(String scheduleName, String courseName) {
        TestContext.put(courseName, TestContext.getSchedule(scheduleName).getAddedCourses().get(0));
    }

    @And("{word} has a course with id {string} term {word} and year {word}")
    public void sHasACourseWithNameTermFallAndYearSenior(String scheduleName, String id, String term, String year) {
        Schedule schedule = TestContext.getSchedule(scheduleName);
        Assert.assertThat(schedule.containsCourse(new Course(id), CalendarTerm.of(term, year)), is(true));
    }

    @When("{word} adds transfer course {string} to schedule with id {word}")
    public void tuckerAddsTransferCourseToScheduleWithIdI(String userName, String courseId, String scheduleIdName) {
        TestController.getInstance().addTransferCourse(TestContext.getSession(userName), courseId, TestContext.getId(scheduleIdName));
    }

    @Then("{word} has a course with id {string}")
    public void sHasACourseWithId(String scheduleName, String id) {
        Schedule schedule = TestContext.getSchedule(scheduleName);

        Assert.assertThat(schedule.containsCourse(new Course(id), new TransferTerm()), is(true));
    }

}