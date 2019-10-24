package majorPlanner.authorizer;

import majorPlanner.entity.Role;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.request.RequestVisitor;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;

public class GatewayBackedAuthorizer implements Authorizer, RequestVisitor<Response> {

    public static final String USER_MISMATCH_MESSAGE = "User cannot create a schedule for another user.";

    @Override
    public Response authorize(Request request) {
        return request.accept(this);
    }

    @Override
    public Response visitCreateScheduleRequest(CreateScheduleRequest createScheduleRequest) {
        if(createScheduleRequest.ownerID.equals(createScheduleRequest.getSession().username)) {
            return new SuccessResponse();
        }
        else if(createScheduleRequest.getSession().role == Role.Admin){
            return new SuccessResponse();
        }
        else {
            return new ErrorResponse(USER_MISMATCH_MESSAGE);
        }
    }
}
