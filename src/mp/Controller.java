import authorizer.Authorizer;
import interactor.Interactor;
import request.Request;
import response.*;

public class Controller {
    public Response createBlankSchedule()
    {
        return new response.ErrorResponse("Story not implemented!");
    }

    private Response executeRequest(Request request, Authorizer authorizer, Interactor interactor)
    {
        Response response = authorizer.authorize(request);
        if (response.containsError()) return response;
        response = interactor.execute(request);
        return response;
    }
}
