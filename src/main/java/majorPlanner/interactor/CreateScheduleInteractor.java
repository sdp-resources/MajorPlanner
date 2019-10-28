package majorPlanner.interactor;

import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.gateway.UserGateway;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;
import org.jetbrains.annotations.NotNull;

public class CreateScheduleInteractor implements Interactor {
    private final UserGateway userGateway;
    private final ScheduleGateway scheduleGateway;

    public CreateScheduleInteractor(UserGateway userGateway, ScheduleGateway scheduleGateway) {
        this.userGateway = userGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public Response execute(Request request) {
        CreateScheduleRequest createScheduleRequest = (CreateScheduleRequest) request;
        User user = userGateway.getUser(createScheduleRequest.ownerID);
        if (user == null) {
            return ErrorResponse.invalidUsername();
        } else {
            Schedule schedule = createAndSaveSchedule(user, createScheduleRequest.name, createScheduleRequest.description);
            return new SuccessResponse<>(schedule);
        }
    }

    @NotNull
    private Schedule createAndSaveSchedule(User user, String name, String description) {
        Schedule schedule = new Schedule(user, name, description);
        scheduleGateway.addSchedule(schedule);
        scheduleGateway.save();
        return schedule;
    }

}
