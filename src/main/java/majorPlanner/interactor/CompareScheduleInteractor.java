package majorPlanner.interactor;

import majorPlanner.entity.Schedule;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.CompareScheduleRequest;
import majorPlanner.response.Response;

public class CompareScheduleInteractor implements Interactor {
    private final CompareScheduleRequest request;
    private final ScheduleGateway scheduleGateway;

    public CompareScheduleInteractor(CompareScheduleRequest request, ScheduleGateway scheduleGateway) {
        this.request = request;
        this.scheduleGateway = scheduleGateway;
    }

    @Override
    public Response execute(){
        Schedule schedule = scheduleGateway.getSchedule(request.scheduleId);
        if (schedule == null) return Response.invalidSchedule();
        return Response.success(schedule.compareScheduleToProgram());
    }
}
