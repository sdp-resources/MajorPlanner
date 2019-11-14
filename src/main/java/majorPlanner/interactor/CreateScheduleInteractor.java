package majorPlanner.interactor;

import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.gateway.UserGateway;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.response.Response;
import org.jetbrains.annotations.NotNull;

public class CreateScheduleInteractor implements Interactor {
    private CreateScheduleRequest request;
    private final UserGateway userGateway;
    private final ScheduleGateway scheduleGateway;

    public CreateScheduleInteractor(CreateScheduleRequest request, UserGateway userGateway, ScheduleGateway scheduleGateway) {
        this.request = request;
        this.userGateway = userGateway;
        this.scheduleGateway = scheduleGateway;
    }

    @Override
    public Response execute() {
        User user = userGateway.getUser(request.ownerID);
        if (user == null) {
            return Response.invalidUsername();
        } else {
            Schedule schedule = createAndSaveSchedule(user, request.name, request.description);
            return Response.success(schedule);
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
