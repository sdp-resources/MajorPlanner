package majorPlanner.interactor;

import majorPlanner.entity.Schedule;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.ViewScheduleRequest;
import majorPlanner.response.Response;

public class ViewScheduleInteractor implements Interactor {
    private final ScheduleGateway scheduleGateway;
    private ViewScheduleRequest request;

    public ViewScheduleInteractor(ViewScheduleRequest request, ScheduleGateway scheduleGateway) {
        this.request = request;
        this.scheduleGateway = scheduleGateway;
    }

    @Override
    public Response execute() {
        Schedule schedule = scheduleGateway.getSchedule(request.scheduleID);
        if (schedule == null) return Response.invalidSchedule();
        return Response.success(schedule);
    }
}
