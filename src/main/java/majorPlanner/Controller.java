package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.gateway.Gateway;
import majorPlanner.interactor.CreateScheduleInteractor;
import majorPlanner.interactor.Interactor;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.response.*;
import majorPlanner.session.Session;

public class Controller {
    private Gateway gateway;
    private Authorizer authorizer;

    public Controller(Gateway gateway, Authorizer authorizer) {
        this.gateway = gateway;
        this.authorizer = authorizer;
    }

    public Response createSchedule(Session session, String username, String scheduleName, String description)
    {
        CreateScheduleRequest request = new CreateScheduleRequest(username, scheduleName, description);
        request.setSession(session);
        return executeRequest(request, new CreateScheduleInteractor(gateway, gateway));
    }

    private Response executeRequest(Request request, Interactor interactor)
    {
        Response response = authorizer.authorize(request);
        if (response.containsError()) return response;
        response = interactor.execute(request);
        return response;
    }
}
