package majorPlanner;

import majorPlanner.response.Response;
import mock.AcceptingCourseGateway;
import mock.RejectingCourseGateway;
import majorPlanner.interactor.AddCourseToScheduleInteractor;
import majorPlanner.request.CreateAddCourseToSchedueRequest;
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

    @Before
    public void setup(){
        rejectCourseGateway = new RejectingCourseGateway();
        acceptCourseGateway = new AcceptingCourseGateway();
        rejectScheduleGateway = new RejectingScheduleGateway();
    }

    @Test
    public void IfCourseIDInvalidReturnError() {
        courseInteractor = new AddCourseToScheduleInteractor(rejectCourseGateway, null);
        CreateAddCourseToSchedueRequest request = new CreateAddCourseToSchedueRequest(COURSE_ID, SCHEDULE_ID);
        Response response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is(true));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseID));
    }

    @Test
    public void IfScheduleIDInvalidReturnError() {
        courseInteractor = new AddCourseToScheduleInteractor(acceptCourseGateway, rejectScheduleGateway);
        CreateAddCourseToSchedueRequest request = new CreateAddCourseToSchedueRequest(COURSE_ID, SCHEDULE_ID);
        Response response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is (true));
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
    }
}
