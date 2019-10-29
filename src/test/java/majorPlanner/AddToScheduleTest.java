package majorPlanner;

import majorPlanner.entity.Term;
import majorPlanner.entity.Year;
import majorPlanner.response.Response;
import mock.AcceptingCourseGateway;
import mock.AcceptingScheduleGateway;
import mock.RejectingCourseGateway;
import majorPlanner.interactor.AddCourseToScheduleInteractor;
import majorPlanner.request.CreateAddCourseToScheduleRequest;
import mock.RejectingScheduleGateway;
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
    private RejectingScheduleGateway rejectScheduleGateway;
    private AcceptingScheduleGateway acceptScheduleGateway;

    @Before
    public void setup(){
        rejectCourseGateway = new RejectingCourseGateway();
        acceptCourseGateway = new AcceptingCourseGateway();
        rejectScheduleGateway = new RejectingScheduleGateway();
        acceptScheduleGateway = new AcceptingScheduleGateway();
    }

    @Test
    public void IfCourseIDInvalidReturnError() {
        courseInteractor = new AddCourseToScheduleInteractor(rejectCourseGateway, null);
        CreateAddCourseToScheduleRequest request = new CreateAddCourseToScheduleRequest(COURSE_ID, SCHEDULE_ID, Term.Fall, Year.Freshman);
        Response response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is(true));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseID));
    }

    @Test
    public void IfScheduleIDInvalidReturnError() {
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, rejectScheduleGateway);
        CreateAddCourseToScheduleRequest request = new CreateAddCourseToScheduleRequest(COURSE_ID, SCHEDULE_ID, Term.Fall, Year.Freshman);
        Response response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is (true));
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
    }

    @Test
    public void IfCourseAndScheduleAreValidReturnSuccessResponse() {
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        CreateAddCourseToScheduleRequest request = new CreateAddCourseToScheduleRequest(COURSE_ID, SCHEDULE_ID, Term.Fall, Year.Freshman);
        Response response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is(false));
        assertThat(acceptScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
    }
}
