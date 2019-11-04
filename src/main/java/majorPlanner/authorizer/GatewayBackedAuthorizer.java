package majorPlanner.authorizer;

import majorPlanner.entity.Course;
import majorPlanner.entity.Role;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.Gateway;
import majorPlanner.request.AddCourseRequest;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.request.RequestVisitor;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;

public class GatewayBackedAuthorizer implements Authorizer, RequestVisitor<Response> {

    public static final String USER_MISMATCH_MESSAGE = "User cannot create a schedule for another user.";
    public static final String SCHEDULE_DOES_NOT_EXIST_MESSAGE = "Schedule does not exist.";
    public static final String COURSE_DOES_NOT_EXIST_MESSAGE = "Course does not exist.";

    private Gateway gateway;

    public GatewayBackedAuthorizer(Gateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Response authorize(Request request) {
        return request.accept(this);
    }

    @Override
    public Response visitCreateScheduleRequest(CreateScheduleRequest createScheduleRequest) {
        if (createScheduleRequest.ownerID.equals(createScheduleRequest.getSession().username)) {
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
        Schedule schedule = gateway.getSchedule(addCourseRequest.scheduleID);
        Course course = gateway.getCourse(addCourseRequest.courseID);
        if (schedule == null)
        {
            return new ErrorResponse(SCHEDULE_DOES_NOT_EXIST_MESSAGE);
        }
        if (course == null)
        {
            return new ErrorResponse(COURSE_DOES_NOT_EXIST_MESSAGE);
        }
        else if (!schedule.getOwner().getUserID().equals(addCourseRequest.getSession().username))
        {
            return new ErrorResponse(USER_MISMATCH_MESSAGE);
        }
        else
        {
            return new SuccessResponse<Void>(null);
        }
    }
}
