package majorPlanner.authorizer;

import majorPlanner.entity.Course;
import majorPlanner.entity.Role;
import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;
import majorPlanner.gateway.Gateway;
import majorPlanner.request.*;
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
    public Response visit(CreateScheduleRequest request) {
        if (request.ownerID.equals(request.getSession().getUsername())) return new SuccessResponse<Void>(null);
        if(request.getSession().getRole() == Role.Admin) return new SuccessResponse<Void>(null);
        return new ErrorResponse(USER_MISMATCH_MESSAGE);
    }

    @Override
    public Response visit(AddCourseRequest request) {
        Schedule schedule = gateway.getSchedule(request.scheduleID);
        Course course = gateway.getCourse(request.courseID);
        if (schedule == null) return new ErrorResponse(SCHEDULE_DOES_NOT_EXIST_MESSAGE);
        if (course == null) return new ErrorResponse(COURSE_DOES_NOT_EXIST_MESSAGE);
        if (!schedule.getOwner().getUserID().equals(request.getSession().getUsername()))
            return new ErrorResponse(USER_MISMATCH_MESSAGE);
        return new SuccessResponse<Void>(null);
    }

    @Override
    public Response visit(ViewScheduleRequest request) {
        Schedule schedule = gateway.getSchedule(request.scheduleID);
        if (schedule == null) return ErrorResponse.invalidSchedule();
        if (!matchesAdminOrUser(schedule.getOwner(), request.getSession().getUser())) {
            return new ErrorResponse(USER_MISMATCH_MESSAGE);
        }
        return new SuccessResponse<Void>(null);
    }

    @Override
    public Response visit(RemoveCourseFromScheduleRequest request) {
        Schedule schedule = gateway.getSchedule(request.scheduleID);
        if (schedule == null) return ErrorResponse.invalidSchedule();
        if (!matchesAdminOrUser(schedule.getOwner(), request.getSession().getUser())) return ErrorResponse.invalidUsername();
        return new SuccessResponse<Void>(null);
    }

    private boolean matchesAdminOrUser(User owner, User user) {
        return owner.equals(user) || owner.isAdmin();
    }
}
