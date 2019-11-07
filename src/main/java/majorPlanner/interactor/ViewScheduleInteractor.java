package majorPlanner.interactor;

import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.Request;
import majorPlanner.request.ViewScheduleRequest;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;

public class ViewScheduleInteractor implements Interactor {
    private final ScheduleGateway scheduleGateway;

    public ViewScheduleInteractor(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public Response executeRequest(ViewScheduleRequest request) {
        scheduleGateway.getSchedule(request.scheduleID);
        return ErrorResponse.invalidSchedule();
    }

    @Override
    public Response execute(Request request) {
        return executeRequest((ViewScheduleRequest) request);
    }
}
