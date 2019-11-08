package acceptance;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import majorPlanner.entity.AddedCourse;
import majorPlanner.entity.Schedule;
import org.junit.Assert;

import static org.hamcrest.CoreMatchers.is;

public class RemoveCourseSteps {
    @When("{word} removes course with id {string} from schedule with id {word}")
    public void removeCourseFromSchedule(String user, String courseID, String scheduleIDName) {
        TestController.getInstance().removeCourse(TestContext.getSession(user), courseID, TestContext.getScheduleId(scheduleIDName));
    }

    @Then("{word} doesn't have a course with id {string} term {word} and year {word}")
    public void sDoesnTHaveACourseWithIdTermFallAndYearSenior(String scheduleName, String courseID, String term, String year) {
        Schedule schedule = TestContext.getSchedule(scheduleName);
        Assert.assertThat(hasCourseWithProperties(schedule, courseID, term, year), is(false));
    }

    private boolean hasCourseWithProperties(Schedule schedule, String id, String term, String year) {
        for (AddedCourse course : schedule.getAddedCourses()) {
            if (course.getCourse().getId().equals(id) && course.getTerm().toString().equals(term) && course.getYear().toString().equals(year)) return true;
        }
        return false;
    }
}
