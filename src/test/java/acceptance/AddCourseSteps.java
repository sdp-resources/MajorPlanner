package acceptance;

import com.sun.nio.sctp.SctpChannel;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import majorPlanner.entity.*;
import majorPlanner.request.ViewScheduleRequest;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;
import majorPlanner.session.Session;
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
        Response response = TestController.getInstance().addCourse(TestContext.getSession(user), TestContext.getCourseId(course), TestContext.getScheduleId(scheduleIDName), period, year);
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

        System.out.println(schedule.getOwner().getUserID());
        System.out.println(schedule.getAddedCourses().size());

        Assert.assertThat(hasCourseWithProperties(schedule, id, term, year), is(true));
    }

    private boolean hasCourseWithProperties(Schedule schedule, String id, String period, String year) {
        CalendarTerm t = CalendarTerm.of(period, year);



        for (AddedCourse course : schedule.getAddedCourses()) {
            System.out.println(id);
            System.out.println(course.getCourse().getId());

            if (course.getCourse().getId().equals(id) && course.getTerm().equals(t)) return true;
        }
        return false;
    }
}