package majorPlanner;

import majorPlanner.entity.*;
import majorPlanner.response.Response;
import mock.*;
import majorPlanner.interactor.addCourseToScheduleInteractor;
import majorPlanner.request.AddCourseRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class AddToScheduleTest {

    private static final String COURSE_ID = "Course1";
    private static final int SCHEDULE_ID = 1234;
    private RejectingCourseGateway rejectCourseGateway;
    private addCourseToScheduleInteractor courseInteractor;
    private AcceptingCourseGateway acceptCourseGateway;
    private ScheduleGatewaySpy rejectScheduleGateway;
    private AcceptingScheduleGateway acceptScheduleGateway;
    private AddCourseRequest request;

    @Before
    public void setup(){
        rejectCourseGateway = new RejectingCourseGateway();
        acceptCourseGateway = new AcceptingCourseGateway();
        rejectScheduleGateway = new RejectingScheduleGateway();
        acceptScheduleGateway = new AcceptingScheduleGateway();
        request = new AddCourseRequest(COURSE_ID, SCHEDULE_ID, Term.Fall, Year.Freshman);
    }

    @Test
    public void IfCourseIDInvalidReturnError() {
        courseInteractor = new addCourseToScheduleInteractor(rejectCourseGateway, null);
        Response response = courseInteractor.executeRequest(request);
        AssertErrorResponse(response, true);
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseID));
    }

    @Test
    public void IfCourseIDValidAndScheduleIDInvalidReturnError() {
        courseInteractor = new addCourseToScheduleInteractor(acceptCourseGateway, rejectScheduleGateway);
        Response response = courseInteractor.executeRequest(request);
        AssertErrorResponse(response, true);
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
    }

    @Test
    public void IfCourseAndScheduleAreValidReturnSuccessResponse() {
        courseInteractor = new addCourseToScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        Response response = courseInteractor.executeRequest(request);
        AssertErrorResponse(response, false);
        assertThat(acceptScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
        Schedule schedule = acceptScheduleGateway.providedSchedule;
        AddedCourse addedCourse = schedule.getAddedCourses().get(0);
        assertThat(schedule.getAddedCourses().size(), is(1));
        AssertRequestedCourseIsAddedCourse(addedCourse);
    }

    @Test
    public void IfCourseAlreadyInScheduleReturnError(){
        courseInteractor = new addCourseToScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        acceptScheduleGateway.providedSchedule.addCourse(new Course(COURSE_ID), Term.Fall, Year.Freshman);
        Response response = courseInteractor.executeRequest(request);
        AssertErrorResponse(response, true);
    }

    private void AssertRequestedCourseIsAddedCourse(AddedCourse addedCourse) {
        assertThat(addedCourse.getTerm(), is(request.term));
        assertThat(addedCourse.getYear(), is(request.year));
        assertThat(addedCourse.getCourse(), is(acceptCourseGateway.providedCourse));
    }

    private void AssertErrorResponse(Response response, boolean b) {
        assertThat(response.containsError(), is(b));
    }
}
