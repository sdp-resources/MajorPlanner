package MajorPlanner;

import majorPlanner.gateway.RejectingUserGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.interactor.CreateScheduleInteractor;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.response.CreateScheduleResponse;
import mock.ScheduleGatewayDummy;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreateScheduleTest {

    private static final String USER_ID = "user1";
    private RejectingUserGateway rejectUserGateway;
    private ScheduleGateway scheduleGateway;
    private CreateScheduleInteractor scheduleInteractor;

    @Before
    public void setup(){
        rejectUserGateway = new RejectingUserGateway();
        scheduleGateway = new ScheduleGatewayDummy();
        scheduleInteractor = new CreateScheduleInteractor(rejectUserGateway, scheduleGateway);
    }


    @Test
    public void invalidUserResponseIsFailure() {
        CreateScheduleRequest request = new CreateScheduleRequest(USER_ID);
        CreateScheduleResponse response = scheduleInteractor.executeRequest(request);
        assertThat(response.message, is(CreateScheduleInteractor.INVALID_USER_MESSAGE));
        assertThat(rejectUserGateway.getRequestedUserID(), is(request.ownerID));
        assertThat(response.ownerID, is(request.ownerID));
    }




}