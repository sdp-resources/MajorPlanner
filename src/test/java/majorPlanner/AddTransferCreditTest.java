package majorPlanner;

import majorPlanner.entity.*;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.interactor.AddCourseToScheduleInteractor;
import majorPlanner.interactor.AddTransferCourseToScheduleInteractor;
import majorPlanner.request.AddTransferCourseToScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.response.Response;
import mock.AcceptingCourseGateway;
import mock.AcceptingScheduleGateway;
import mock.RejectingCourseGateway;
import mock.RejectingScheduleGateway;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AddTransferCreditTest {

    private static final String COURSE_ID = "CS220";
    private static final int SCHEDULE_ID = 123;
    private static final Term TRANSFER_TERM = new TransferTerm();
    private final RejectingCourseGateway rejectCourseGateway = new RejectingCourseGateway();
    private final AcceptingScheduleGateway acceptScheduleGateway = new AcceptingScheduleGateway();
    private AddTransferCourseToScheduleInteractor courseInteractor;
    private AcceptingCourseGateway acceptCourseGateway = new AcceptingCourseGateway();
    private RejectingScheduleGateway rejectScheduleGateway = new RejectingScheduleGateway();
    private Response response;
    private AddTransferCourseToScheduleRequest request;

    @Before
    public void setUp(){
        request = new AddTransferCourseToScheduleRequest(COURSE_ID, SCHEDULE_ID, TRANSFER_TERM);
    }

    @Test
    public void whenCourseIDInvalid_ReturnError() {
        courseInteractor = new AddTransferCourseToScheduleInteractor(rejectCourseGateway, acceptScheduleGateway);
        response = courseInteractor.executeRequest(request);
        assertThat(response, is(Response.invalidCourse()));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseId));
        assertSaveWasNotCalled();
    }

    @Test
    public void whenCourseIDValidAndScheduleIDInvalid_ReturnError() {
        courseInteractor = new AddTransferCourseToScheduleInteractor(acceptCourseGateway, rejectScheduleGateway);
        response = courseInteractor.executeRequest(request);
        assertThat(response, is(Response.invalidSchedule()));
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleId));
        assertSaveWasNotCalled();
    }

//    @Test
//    public void whenCourseAndScheduleAreValid_CourseAdded() {
//        courseInteractor = new AddTransferCourseToScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
//        response = courseInteractor.executeRequest(request);
//        assertSuccessfulResponse();
//        assertThat(acceptScheduleGateway.getRequestedScheduleID(), is(request.scheduleId));
//        assertNumberCoursesInScheduleIs(acceptScheduleGateway.returnedSchedule, 1);
//        assertRequestedCourseIsAddedCourse(acceptScheduleGateway.returnedSchedule.getAddedCourses().get(0));
//        assertSaveWasCalled();
//    }

//    private void assertRequestedCourseIsAddedCourse(AddedCourse addedCourse) {
//        Term t = Term.of(request.period, request.year);
//        assertThat(addedCourse.getTerm(), is(t));
//        assertThat(addedCourse.getCourse(), is(acceptCourseGateway.providedCourse));
//    }
//
//    private void assertNumberCoursesInScheduleIs(Schedule schedule, int numCourses) {
//        assertThat(schedule.getAddedCourses().size(), is(numCourses));
//    }
//    private void assertSuccessfulResponse() {
//        assertThat(response.containsError(), is(false));
//    }

    private void assertSaveWasNotCalled() {
        assertThat(acceptScheduleGateway.saveCalled(), is(false));
    }
}
