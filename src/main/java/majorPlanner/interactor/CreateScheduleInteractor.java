package majorPlanner.interactor;

import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.gateway.UserGateway;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessfulResponse;

public class CreateScheduleInteractor implements Interactor {
    public static final String INVALID_USER_MESSAGE = "Invalid User";
    private final UserGateway userGateway;
    private final ScheduleGateway scheduleGateway;

    public CreateScheduleInteractor(UserGateway userGateway, ScheduleGateway scheduleGateway) {
        this.userGateway = userGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public Response execute(Request request) {
        CreateScheduleRequest createScheduleRequest = (CreateScheduleRequest) request;
        User user = userGateway.getUser(createScheduleRequest.ownerID);
        if(user == null){
            return new ErrorResponse(INVALID_USER_MESSAGE);
        }else{
            return new SuccessfulResponse<Schedule>();
        }
    }
}
