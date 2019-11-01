package majorPlanner.authorizer;

import majorPlanner.entity.Role;
import majorPlanner.gateway.Gateway;
import majorPlanner.request.AddCourseRequest;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.request.RequestVisitor;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;
import webserver.MemoryGateway;

public class GatewayBackedAuthorizer implements Authorizer, RequestVisitor<Response> {

    public static final String USER_MISMATCH_MESSAGE = "User cannot create a schedule for another user.";

    public GatewayBackedAuthorizer(Gateway gateway) {
    }

    @Override
    public Response authorize(Request request) {
        return request.accept(this);
    }

    @Override
    public Response visitCreateScheduleRequest(CreateScheduleRequest createScheduleRequest) {
        if(createScheduleRequest.ownerID.equals(createScheduleRequest.getSession().username)) {
            return new SuccessResponse<Void>(null);
        }
        else if(createScheduleRequest.getSession().role == Role.Admin){
            return new SuccessResponse<Void>(null);
        }
        else {
            return new ErrorResponse(USER_MISMATCH_MESSAGE);
        }
    }

    @Override
    public Response visitAddCourseRequest(AddCourseRequest addCourseRequest) {
        return new ErrorResponse("dummy message");
    }
}
