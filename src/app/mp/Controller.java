package mp;

import mp.authorizer.Authorizer;
import mp.interactor.Interactor;
import mp.request.Request;
import mp.response.*;

public class Controller {
    public Response createSchedule()
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
