package majorPlanner;

import majorPlanner.entity.Schedule;
import majorPlanner.interactor.CreateScheduleInteractor;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;
import mock.AcceptingUserGateway;
import mock.RejectingUserGateway;
import mock.ScheduleGatewaySpy;
import mock.UserGatewaySpy;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreateScheduleTest {

    private static final String USER_ID = "user1";
    private static final String name = "schedule name";
    private static final String description = "a description";
    private ScheduleGatewaySpy scheduleGateway = new ScheduleGatewaySpy();
    private CreateScheduleRequest request;
    private UserGatewaySpy userGatewaySpy;
    private CreateScheduleInteractor scheduleInteractor;
    private Response response;

    @Before
    public void setUp() {
        request = new CreateScheduleRequest(USER_ID, name, description);
    }

    @Test
    public void invalidUserResponseIsFailure() {
        userGatewaySpy = new RejectingUserGateway();
        scheduleInteractor = new CreateScheduleInteractor(request, userGatewaySpy, scheduleGateway);
        response = scheduleInteractor.execute();
        assertErrorResponse();
        assertGatewayWasAskedForUser(request.ownerID);
    }

    @Test
    public void userIsValid_scheduleCreated() {
        userGatewaySpy = new AcceptingUserGateway();
        scheduleInteractor = new CreateScheduleInteractor(request, userGatewaySpy, scheduleGateway);
        response = scheduleInteractor.execute();
        assertSuccessfulResponseContainsProvidedSchedule(scheduleGateway.getProvidedSchedule());
        assertGatewayWasAskedForUser(request.ownerID);
        assertCreatedScheduleHasInfoMatchingRequest(scheduleGateway.getProvidedSchedule());
        assertSaveCalled();
    }

    private void assertSuccessfulResponseContainsProvidedSchedule(Schedule providedSchedule) {
        assertThat(response.containsError(), is(false));
        SuccessResponse<Schedule> successResponse = (SuccessResponse<Schedule>) response;
        Schedule returnedSchedule = successResponse.getValue();
        assertThat(providedSchedule, is(returnedSchedule));
    }

    private void assertErrorResponse() {
        assertThat(response.containsError(), is(true));
    }

    private void assertGatewayWasAskedForUser(String userID) {
        assertThat(userGatewaySpy.getRequestedUserID(), is(userID));
    }

    private void assertSaveCalled() {
        assertThat(scheduleGateway.saveCalled(), is(true));
    }

    private void assertCreatedScheduleHasInfoMatchingRequest(Schedule schedule) {
        assertThat(schedule.getOwner().getUserID(), is((request.ownerID)));
        assertThat(schedule.getDescription(), is((request.description)));
        assertThat(schedule.getName(), is((request.name)));
    }

}
