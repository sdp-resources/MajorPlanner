package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.gateway.Gateway;
import majorPlanner.interactor.Interactor;
import majorPlanner.request.Request;
import majorPlanner.response.*;
import majorPlanner.session.Session;

public class Controller {
    private Gateway gateway;

    public Controller(Gateway gateway) {
        this.gateway = gateway;
    }

    public Response createSchedule(Session session, String username, String scheduleName, String description)
    {
        return new ErrorResponse("Story not implemented!");
    }

    private Response executeRequest(Request request, Authorizer authorizer, Interactor interactor)
    {
        Response response = authorizer.authorize(request);
        if (response.containsError()) return response;
        response = interactor.execute(request);
        return response;
    }
}
