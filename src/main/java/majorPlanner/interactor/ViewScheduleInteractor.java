package majorPlanner.interactor;

import majorPlanner.entity.Schedule;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.Request;
import majorPlanner.request.ViewScheduleRequest;
import majorPlanner.response.Response;

public class ViewScheduleInteractor implements Interactor {
    private final ScheduleGateway scheduleGateway;

    public ViewScheduleInteractor(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public Response executeRequest(ViewScheduleRequest request) {
        Schedule schedule = scheduleGateway.getSchedule(request.scheduleID);
        if (schedule == null) return Response.invalidSchedule();
        return Response.success(schedule);
    }

    @Override
    public Response execute(Request request) {
        return executeRequest((ViewScheduleRequest) request);
    }
}
