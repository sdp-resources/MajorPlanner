package majorPlanner;

import majorPlanner.entity.Course;
import majorPlanner.entity.Period;
import majorPlanner.entity.Schedule;
import majorPlanner.entity.Year;
import majorPlanner.interactor.RemoveCourseFromScheduleInteractor;
import majorPlanner.request.RemoveCourseFromScheduleRequest;
import majorPlanner.response.Response;
import mock.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class RemoveCourseFromScheduleTest {

    private static final String COURSE_ID = "Course1";
    private String ADDITIONAL_COURSE_ID = "4321";
    private static final int SCHEDULE_ID = 1234;
    private AcceptingCourseGateway acceptCourseGateway;
    private RejectingCourseGateway rejectCourseGateway;
    private ScheduleGatewaySpy rejectScheduleGateway;
    private RemoveCourseFromScheduleInteractor courseInteractor;
    private RemoveCourseFromScheduleRequest request;
    private Response response;
    private AcceptingScheduleGateway acceptScheduleGateway;

    @Before
    public void setup(){
        rejectCourseGateway = new RejectingCourseGateway();
        acceptCourseGateway = new AcceptingCourseGateway();
        rejectScheduleGateway = new RejectingScheduleGateway();
        acceptScheduleGateway = new AcceptingScheduleGateway();
        request = new RemoveCourseFromScheduleRequest(COURSE_ID, SCHEDULE_ID);
    }

    @Test
    public void removeCourseThatDoesNotExist(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(request, rejectCourseGateway, acceptScheduleGateway);
        executeRequest();
        assertThat(response, is(Response.invalidCourse()));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseID));
        assertSaveWasNotCalled();
    }

    @Test
    public void deleteCourseFromScheduleThatDoesNotExist(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(request, acceptCourseGateway, rejectScheduleGateway);
        executeRequest();
        assertThat(response, is(Response.invalidSchedule()));
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
        assertSaveWasNotCalled();
    }

    @Test
    public void whenDeletingCourseFromEmptyScheduleGetAnError(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(request, acceptCourseGateway, acceptScheduleGateway);
        Schedule schedule = createSchedule();
        executeRequest();
        assertThat(schedule.getID(), is(SCHEDULE_ID));
        assertThat(response, is(Response.emptySchedule()));
        assertSaveWasNotCalled();
    }

    @Test
    public void deleteCourseFromSchedule(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(request, acceptCourseGateway, acceptScheduleGateway);
        Schedule schedule = createSchedule();
        schedule.addCourse(new Course(COURSE_ID), Period.Fall.toString(), Year.Freshman.toString());
        executeRequest();
        assertThat((response.containsError()), is(false));
        assertSaveWasCalled();
    }

    @Test
    public void deleteCourseThatScheduleDoesNotHave(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(request, acceptCourseGateway, acceptScheduleGateway);
        Schedule schedule = createSchedule();
        schedule.addCourse(new Course(ADDITIONAL_COURSE_ID), Period.Fall.toString(), Year.Freshman.toString());
        executeRequest();
        assertThat(response, is(Response.courseNotInSchedule()));
        assertSaveWasNotCalled();
    }

    private Schedule createSchedule() {
        return acceptScheduleGateway.returnedSchedule;
    }

    private void executeRequest() {
        response = courseInteractor.execute();
    }

    private void assertSaveWasCalled() {
        Assert.assertThat(acceptScheduleGateway.saveCalled(), is(true));
    }

    private void assertSaveWasNotCalled() {
        Assert.assertThat(acceptScheduleGateway.saveCalled(), is(false));
    }

}
