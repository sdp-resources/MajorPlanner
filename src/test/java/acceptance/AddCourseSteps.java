package acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import majorPlanner.entity.*;
import majorPlanner.response.Response;
import org.junit.Assert;

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
        TestController.getInstance().defineCourse(name, id);
        TestContext.put(name, id);
    }

    @When("{word} adds {string} to schedule with id {word} for {word} of {word} year")
    public void userAddsCourseToSchedule(String user, String course, String scheduleIDName, String period, String year) {
        Response response = TestController.getInstance().addCourse(TestContext.getSession(user), TestContext.getCourseId(course), TestContext.getId(scheduleIDName), period, year);
    }

    @Then("{word} has the course, {string} during {word} of {word} year")
    public void scheduleHasTheCourse(String schedule, String course, String term, String year) {
        Assert.assertThat(TestController.getInstance().scheduleHasCourse(schedule, course, term, year), is(true));
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
    public void tuckerAddsTransferCourseToScheduleWithIdI(String userName, String courseName, String scheduleIdName) {
        TestController.getInstance().addTransferCourse(TestContext.getSession(userName), TestContext.getCourseId(courseName), TestContext.getId(scheduleIdName));
    }

    @Then("{word} has a course with id {string}")
    public void sHasACourseWithId(String scheduleName, String id) {
        Schedule schedule = TestContext.getSchedule(scheduleName);

        Assert.assertThat(schedule.containsCourse(new Course(id), new TransferTerm()), is(true));
    }

}