package majorPlanner;

import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.interactor.CreateScheduleInteractor;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.response.Response;
import mock.AcceptingUserGateway;
import mock.RejectingUserGateway;
import mock.ScheduleGatewaySpy;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreateScheduleTest {

    private static final String USER_ID = "user1";

    @Test
    public void invalidUserResponseIsFailure() {
        RejectingUserGateway rejectUserGateway = new RejectingUserGateway();
        ScheduleGateway scheduleGateway = new ScheduleGatewaySpy();
        CreateScheduleInteractor scheduleInteractor = new CreateScheduleInteractor(rejectUserGateway, scheduleGateway);
        CreateScheduleRequest request = new CreateScheduleRequest(USER_ID);
        Response response = scheduleInteractor.execute(request);
        assertThat(response.containsError(), is(true));
        assertThat(rejectUserGateway.getRequestedUserID(), is(request.ownerID));
    }

    @Test
    public void userIsValid_scheduleCreated() {
        AcceptingUserGateway acceptingUserGateway = new AcceptingUserGateway();
        ScheduleGatewaySpy scheduleGateway = new ScheduleGatewaySpy();
        CreateScheduleInteractor scheduleInteractor = new CreateScheduleInteractor(acceptingUserGateway, scheduleGateway);
        CreateScheduleRequest request = new CreateScheduleRequest(USER_ID);
        Response response = scheduleInteractor.execute(request);
        assertThat(acceptingUserGateway.getRequestedUserID(), is(request.ownerID));
//        assertThat(scheduleGateway.getProvidedSchedule(), is((response.ownerID)));
//        assertThat(scheduleGateway.getProvidedSchedule(), is((response.description)));
//        assertThat(scheduleGateway.getProvidedSchedule(), is((response.name)));
        assertThat(response.containsError(), is(false));
//        assertThat(ScheduleGatewayDummy.savedCalled(), is(true));
    }
}
