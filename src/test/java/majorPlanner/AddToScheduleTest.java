package majorPlanner;

import majorPlanner.entity.*;
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
    private static final int SCHEDULE_ID = 1234;
    private RejectingCourseGateway rejectCourseGateway;
    private AddCourseToScheduleInteractor courseInteractor;
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
        courseInteractor = new AddCourseToScheduleInteractor(rejectCourseGateway, null);
        Response response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is(true));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseID));
    }

    @Test
    public void IfCourseIDValidAndScheduleIDInvalidReturnError() {
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, rejectScheduleGateway);
        Response response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is (true));
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
    }

    @Test
    public void IfCourseAndScheduleAreValidReturnSuccessResponse() {
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        Response response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is(false));
        assertThat(acceptScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
        Schedule schedule = acceptScheduleGateway.providedSchedule;
        AddedCourse addedCourse = schedule.getAddedCourses().get(0);;
        assertThat(schedule.getAddedCourses().size(), is(1));
        assertThat(addedCourse.getTerm(), is(request.term));
        assertThat(addedCourse.getYear(), is(request.year));
        assertThat(addedCourse.getCourse(), is(acceptCourseGateway.providedCourse));
    }

    @Test
    public void IfCourseAlreadyInScheduleReturnError(){
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        acceptScheduleGateway.providedSchedule.addCourse(acceptCourseGateway.providedCourse, Term.Fall, Year.Freshman);
        Response response = courseInteractor.executeRequest(request);
        //assertThat(response.containsError(), is(true));
    }
}
