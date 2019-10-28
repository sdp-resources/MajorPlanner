package mock;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.request.Request;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;

public class AcceptingAuthorizer implements Authorizer {
    @Override
    public Response authorize(Request request) {
        return new SuccessResponse<Void>(null);
    }
}
