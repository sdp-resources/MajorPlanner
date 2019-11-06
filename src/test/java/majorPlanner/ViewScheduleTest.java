package majorPlanner;

import majorPlanner.interactor.ViewScheduleInteractor;
import majorPlanner.request.ViewScheduleRequest;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import mock.RejectingScheduleGateway;
import mock.ScheduleGatewaySpy;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ViewScheduleTest {

    private ViewScheduleInteractor viewScheduleInteractor;
    private ScheduleGatewaySpy rejectingScheduleGateway = new RejectingScheduleGateway();
    private ViewScheduleRequest request;
    private int scheduleID = 1234;

    @Test
    public void whenScheduleIdInvalid_ReturnErrorResponse() {
        viewScheduleInteractor = new ViewScheduleInteractor(rejectingScheduleGateway);
        request = new ViewScheduleRequest(scheduleID);
        Response response = viewScheduleInteractor.executeRequest(request);
        assertThat(response, is(ErrorResponse.invalidSchedule()));
        assertThat(rejectingScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
    }

}
