package majorPlanner;

import mock.RejectingCourseGateway;
import majorPlanner.interactor.AddCourseToScheduleInteractor;
import majorPlanner.request.CreateAddCourseToSchedueRequest;
import majorPlanner.response.CreateAddCourseToScheduleResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class AddToScheduleTest {

    private static final String COURSE_ID = "Course1";
    private RejectingCourseGateway rejectCourseGateway;
    private AddCourseToScheduleInteractor courseInteractor;

    @Before
    public void setup(){
        rejectCourseGateway = new RejectingCourseGateway();
        courseInteractor = new AddCourseToScheduleInteractor(rejectCourseGateway, null);
    }

    @Test
    public void IfCourseIDInvalidReturnError() {
        CreateAddCourseToSchedueRequest request = new CreateAddCourseToSchedueRequest(COURSE_ID);
        CreateAddCourseToScheduleResponse response = courseInteractor.executeRequest(request);
        assertThat(response.message, is(AddCourseToScheduleInteractor.INVALID_COURSE_MESSAGE));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseID));

    }
}
