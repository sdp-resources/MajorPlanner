package mock;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.request.Request;
import majorPlanner.response.Response;

public class AcceptingAuthorizer implements Authorizer {
    @Override
    public Response authorize(Request request) {
        return Response.ok();
    }

}
