package majorPlanner;

import majorPlanner.entity.*;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import mock.*;
import majorPlanner.interactor.AddCourseToScheduleInteractor;
import majorPlanner.request.AddCourseRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class AddToScheduleTest {

    private static final String COURSE_ID = "Course1";
    private static final String COURSE_ID_OTHER = "Course2";
    private static final int SCHEDULE_ID = 1234;
    private RejectingCourseGateway rejectCourseGateway;
    private AddCourseToScheduleInteractor courseInteractor;
    private AcceptingCourseGateway acceptCourseGateway;
    private ScheduleGatewaySpy rejectScheduleGateway;
    private AcceptingScheduleGateway acceptScheduleGateway;
    private AddCourseRequest request;
    private Response response;

    @Before
    public void setup(){
        rejectCourseGateway = new RejectingCourseGateway();
        acceptCourseGateway = new AcceptingCourseGateway();
        rejectScheduleGateway = new RejectingScheduleGateway();
        acceptScheduleGateway = new AcceptingScheduleGateway();
        request = new AddCourseRequest(COURSE_ID, SCHEDULE_ID, Term.Fall.toString(), Year.Freshman.toString());
    }

    @Test
    public void whenCourseIDInvalid_ReturnError() {
        courseInteractor = new AddCourseToScheduleInteractor(rejectCourseGateway, acceptScheduleGateway);
        response = courseInteractor.executeRequest(request);
        assertThat(response, is(ErrorResponse.invalidCourse()));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseID));
        assertSaveWasNotCalled();
    }

    @Test
    public void whenCourseIDValidAndScheduleIDInvalid_ReturnError() {
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, rejectScheduleGateway);
        response = courseInteractor.executeRequest(request);
        assertThat(response, is(ErrorResponse.invalidSchedule()));
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
        assertSaveWasNotCalled();
    }

    @Test
    public void whenCourseAndScheduleAreValid_CourseAdded() {
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        response = courseInteractor.executeRequest(request);
        assertSuccessfulResponse();
        assertThat(acceptScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
        assertNumberCoursesInScheduleIs(acceptScheduleGateway.returnedSchedule, 1);
        assertRequestedCourseIsAddedCourse(acceptScheduleGateway.returnedSchedule.getAddedCourses().get(0));
        assertSaveWasCalled();
    }

    @Test
    public void whenCourseAlreadyInSchedule_ReturnError(){
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        acceptScheduleGateway.returnedSchedule.addCourse(new Course(COURSE_ID), Term.Fall.toString(), Year.Freshman.toString());
        response = courseInteractor.executeRequest(request);
        assertThat(response, is(ErrorResponse.previouslyAddedCourse()));
        assertSaveWasNotCalled();
    }

    @Test
    public void WhenAddingNewCourseToPopulatedSchedule_CourseAdded(){
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        acceptScheduleGateway.returnedSchedule.addCourse(new Course(COURSE_ID_OTHER), Term.Fall.toString(), Year.Freshman.toString());
        response = courseInteractor.executeRequest(request);
        assertSuccessfulResponse();
        assertThat(acceptScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
        assertNumberCoursesInScheduleIs(acceptScheduleGateway.returnedSchedule, 2);
        assertRequestedCourseIsAddedCourse(acceptScheduleGateway.returnedSchedule.getAddedCourses().get(1));
        assertSaveWasCalled();
    }

    private void assertRequestedCourseIsAddedCourse(AddedCourse addedCourse) {
        assertThat(addedCourse.getTerm().toString(), is(request.term));
        assertThat(addedCourse.getYear().toString(), is(request.year));
        assertThat(addedCourse.getCourse(), is(acceptCourseGateway.providedCourse));
    }

    private void assertNumberCoursesInScheduleIs(Schedule schedule, int numCourses) {
        assertThat(schedule.getAddedCourses().size(), is(numCourses));
    }

    private void assertSuccessfulResponse() {
        assertThat(response.containsError(), is(false));
    }

    private void assertSaveWasCalled() {
        assertThat(acceptScheduleGateway.saveCalled(), is(true));
    }

    private void assertSaveWasNotCalled() {
        assertThat(acceptScheduleGateway.saveCalled(), is(false));
    }

}
