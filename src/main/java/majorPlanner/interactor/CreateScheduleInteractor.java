package majorPlanner.interactor;

import majorPlanner.entity.User;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.gateway.UserGateway;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.response.CreateScheduleResponse;

public class CreateScheduleInteractor {
    public static final String INVALID_USER_MESSAGE = "Invalid User";
    private final UserGateway userGateway;
    private final ScheduleGateway scheduleGateway;

    public CreateScheduleInteractor(UserGateway userGateway, ScheduleGateway scheduleGateway) {
        this.userGateway = userGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public CreateScheduleResponse executeRequest(CreateScheduleRequest request) {
        User user = userGateway.getUser(request.ownerID);
        return new CreateScheduleResponse(INVALID_USER_MESSAGE, request.ownerID);
    }
}
