package majorPlanner;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.entity.Term;
import majorPlanner.entity.Year;
import majorPlanner.interactor.RemoveCourseFromScheduleInteractor;
import majorPlanner.request.RemoveCourseFromScheduleRequest;
import majorPlanner.response.ErrorResponse;
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
    }

    @Test
    public void removeCourseThatDoesNotExist(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(rejectCourseGateway, acceptScheduleGateway);
        createCourseRemovalRequest();
        executeRequest();
        assertThat(response, is(ErrorResponse.invalidCourse()));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseID));
        assertSaveWasNotCalled();
    }

    @Test
    public void deleteCourseFromScheduleThatDoesNotExist(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(acceptCourseGateway, rejectScheduleGateway);
        createCourseRemovalRequest();
        executeRequest();
        assertThat(response, is(ErrorResponse.invalidSchedule()));
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
        assertSaveWasNotCalled();
    }

    @Test
    public void whenDeletingCourseFromEmptyScheduleGetAnError(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        Schedule schedule = createSchedule();
        createCourseRemovalRequest();
        executeRequest();
        assertThat(schedule.getID(), is(SCHEDULE_ID));
        assertThat(response, is(ErrorResponse.emptySchedule()));
        assertSaveWasNotCalled();
    }

    @Test
    public void deleteCourseFromSchedule(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        Schedule schedule = createSchedule();
        schedule.addCourse(new Course(COURSE_ID), Term.Fall, Year.Freshman);
        createCourseRemovalRequest();
        executeRequest();
        assertThat((response.containsError()), is(false));
        assertSaveWasCalled();
    }

    @Test
    public void deleteCourseThatScheduleDoesNotHave(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        Schedule schedule = createSchedule();
        schedule.addCourse(new Course(ADDITIONAL_COURSE_ID), Term.Fall, Year.Freshman);
        createCourseRemovalRequest();
        executeRequest();
        assertThat(response, is(ErrorResponse.courseNotInSchedule()));
        assertSaveWasNotCalled();
    }

    private Schedule createSchedule() {
        return acceptScheduleGateway.returnedSchedule;
    }

    private void createCourseRemovalRequest() {
        request = new RemoveCourseFromScheduleRequest(COURSE_ID, SCHEDULE_ID);
    }

    private void executeRequest() {
        response = courseInteractor.executeRequest(request);
    }

    private void assertSaveWasCalled() {
        Assert.assertThat(acceptScheduleGateway.saveCalled(), is(true));
    }

    private void assertSaveWasNotCalled() {
        Assert.assertThat(acceptScheduleGateway.saveCalled(), is(false));
    }

}
