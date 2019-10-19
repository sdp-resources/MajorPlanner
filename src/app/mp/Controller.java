package mp;

import mp.authorizer.Authorizer;
import mp.gateway.Gateway;
import mp.interactor.Interactor;
import mp.request.Request;
import mp.response.*;
import mp.session.Session;

public class Controller {
    private Gateway gateway;

    public Controller(Gateway gateway) {
        this.gateway = gateway;
    }

    public Response createSchedule(Session session, String username, String scheduleName)
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
