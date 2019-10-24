package majorPlanner.authorizer;

import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.request.RequestVisitor;
import majorPlanner.response.Response;

public class GatewayBackedAuthorizer implements Authorizer, RequestVisitor<Response> {

    @Override
    public Response authorize(Request request) {
        return request.accept(this);
    }

    @Override
    public Response visitCreateScheduleRequest(CreateScheduleRequest createScheduleRequest) {
        return null;
    }
}
