package majorPlanner;

import majorPlanner.entity.*;
import majorPlanner.interactor.AddTransferCourseToScheduleInteractor;
import majorPlanner.request.AddTransferCourseToScheduleRequest;
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
    private static final String COURSE_ID_OTHER = "CS223";
    private static final String COURSE_ID_ANOTHER = "CS225";
    private static final int SCHEDULE_ID = 123;
    private AcceptingCourseGateway acceptCourseGateway;
    private RejectingCourseGateway rejectCourseGateway;
    private AcceptingScheduleGateway acceptScheduleGateway;
    private RejectingScheduleGateway rejectScheduleGateway;
    private AddTransferCourseToScheduleInteractor courseInteractor;
    private Response response;
    private AddTransferCourseToScheduleRequest request;

    @Before
    public void setUp(){
        acceptCourseGateway = new AcceptingCourseGateway();
        rejectCourseGateway = new RejectingCourseGateway();
        acceptScheduleGateway = new AcceptingScheduleGateway();
        rejectScheduleGateway = new RejectingScheduleGateway();
        request = new AddTransferCourseToScheduleRequest(COURSE_ID, SCHEDULE_ID);
    }

    @Test
    public void whenCourseIDInvalid_ReturnError() {
        courseInteractor = new AddTransferCourseToScheduleInteractor(request, rejectCourseGateway, acceptScheduleGateway);
        response = courseInteractor.execute();
        assertThat(response, is(Response.invalidCourse()));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseId));
        assertSaveWasNotCalled();
    }

    @Test
    public void whenCourseIDValidAndScheduleIDInvalid_ReturnError() {
        courseInteractor = new AddTransferCourseToScheduleInteractor(request, acceptCourseGateway, rejectScheduleGateway);
        response = courseInteractor.execute();
        assertThat(response, is(Response.invalidSchedule()));
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleId));
        assertSaveWasNotCalled();
    }

    @Test
    public void whenCourseAndScheduleAreValid_CourseAdded() {
        courseInteractor = new AddTransferCourseToScheduleInteractor(request, acceptCourseGateway, acceptScheduleGateway);
        response = courseInteractor.execute();
        assertSuccessfulResponse();
        assertThat(acceptScheduleGateway.getRequestedScheduleID(), is(request.scheduleId));
        assertNumberCoursesInScheduleIs(acceptScheduleGateway.returnedSchedule, 1);
        assertRequestedCourseIsAddedCourse(acceptScheduleGateway.returnedSchedule.getAddedCourses().get(0));
        assertSaveWasCalled();
    }

    @Test
    public void whenCourseAlreadyInSchedule_ReturnError(){
        courseInteractor = new AddTransferCourseToScheduleInteractor(request, acceptCourseGateway, acceptScheduleGateway);
        acceptScheduleGateway.returnedSchedule.addTransferCourse(new Course(COURSE_ID));
        response = courseInteractor.execute();
        assertThat(response, is(Response.previouslyAddedCourse()));
        assertSaveWasNotCalled();
    }

    @Test
    public void whenAddingNewCourseToPopulatedScheduleCourseAdded(){
        courseInteractor = new AddTransferCourseToScheduleInteractor(request, acceptCourseGateway, acceptScheduleGateway);
        createPopulatedSchedule();
        response = courseInteractor.execute();
        assertSuccessfulResponse();
        assertThat(acceptScheduleGateway.getRequestedScheduleID(), is(request.scheduleId));
        assertNumberCoursesInScheduleIs(acceptScheduleGateway.returnedSchedule, 3);
        assertRequestedCourseIsAddedCourse(acceptScheduleGateway.returnedSchedule.getAddedCourses().get(2));
        assertSaveWasCalled();
    }

    @Test
    public void whenTransferCourseIsAlreadyCalendarCourse() {
        courseInteractor = new AddTransferCourseToScheduleInteractor(request, acceptCourseGateway, acceptScheduleGateway);
        acceptScheduleGateway.returnedSchedule.addCourse(new Course(COURSE_ID), "Fall", "Freshman");
        response = courseInteractor.execute();
        assertThat(response, is(Response.previouslyAddedCourse()));
        assertSaveWasNotCalled();
    }

    private void assertRequestedCourseIsAddedCourse(AddedCourse addedCourse) {
        Term t = new TransferTerm();
        assertThat(addedCourse.getTerm(), is(t));
        assertThat(addedCourse.getCourse(), is(acceptCourseGateway.providedCourse));
    }

    private void createPopulatedSchedule() {
        acceptScheduleGateway.returnedSchedule.addTransferCourse(new Course(COURSE_ID_OTHER));
        acceptScheduleGateway.returnedSchedule.addCourse(new Course(COURSE_ID_ANOTHER), "Fall", "Freshman");
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
