package majorPlanner;

import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.interactor.ViewScheduleInteractor;
import majorPlanner.request.ViewScheduleRequest;
import majorPlanner.response.Response;
import mock.AcceptingScheduleGateway;
import mock.RejectingScheduleGateway;
import mock.ScheduleGatewaySpy;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ViewScheduleTest {

    private ViewScheduleInteractor viewScheduleInteractor;
    private ViewScheduleRequest request;
    private int scheduleID = 1234;

    @Before
    public void setUp() {
        request = new ViewScheduleRequest(scheduleID);
    }

    @Test
    public void whenScheduleIdInvalid_ReturnErrorResponse() {
        ScheduleGatewaySpy rejectingScheduleGateway = new RejectingScheduleGateway();
        viewScheduleInteractor = new ViewScheduleInteractor(rejectingScheduleGateway);
        Response response = viewScheduleInteractor.executeRequest(request);
        assertThat(response, is(Response.invalidSchedule()));
        assertThat(rejectingScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
    }

    @Test
    public void whenScheduleIdValid_ReturnSchedule(){
        AcceptingScheduleGateway acceptingScheduleGateway = new AcceptingScheduleGateway();
        viewScheduleInteractor = new ViewScheduleInteractor(acceptingScheduleGateway);
        Response response = viewScheduleInteractor.executeRequest(request);
        assertThat(response, is(Response.success(acceptingScheduleGateway.returnedSchedule)));
        assertThat(acceptingScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
    }

}
