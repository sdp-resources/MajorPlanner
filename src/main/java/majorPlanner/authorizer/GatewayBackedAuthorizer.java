package majorPlanner.authorizer;

import majorPlanner.entity.Course;
import majorPlanner.entity.Role;
import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;
import majorPlanner.gateway.Gateway;
import majorPlanner.request.*;
import majorPlanner.response.Response;

public class GatewayBackedAuthorizer implements Authorizer, RequestVisitor<Response> {

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
        if (request.ownerID.equals(request.getSession().getUser().getUserID())) return Response.ok();
        if (request.getSession().getRole() == Role.Admin) return Response.ok();
        return Response.userMismatch();
    }

    @Override
    public Response visit(AddCourseRequest request) {
        Schedule schedule = gateway.getSchedule(request.scheduleId);
        Course course = gateway.getCourse(request.courseId);
        return checkAddCourse(schedule, course, request);
    }

    @Override
    public Response visit(AddTransferCourseToScheduleRequest request) {
        Schedule schedule = gateway.getSchedule(request.scheduleId);
        Course course = gateway.getCourse(request.courseId);
        return checkAddCourse(schedule, course, request);
    }

    @Override
    public Response visit(ViewCourseListRequest request) {
        return Response.ok();
    }

    @Override
    public Response visit(CompareScheduleRequest request) {
        return Response.ok();
    }

    private Response checkAddCourse(Schedule schedule, Course course, Request request) {
        if (schedule == null) return Response.nonExistentSchedule();
        if (course == null) return Response.nonExistentCourse();
        if (!matchesAdminOrUser(schedule.getOwner(), request.getSession().getUser()))
            return Response.userMismatch();
        return Response.ok();
    }

    @Override
    public Response visit(ViewScheduleRequest request) {
        Schedule schedule = gateway.getSchedule(request.scheduleID);
        if (schedule == null) return Response.invalidSchedule();
        if (!matchesAdminOrUser(schedule.getOwner(), request.getSession().getUser())) {
            return Response.userMismatch();
        }
        return Response.ok();
    }

    @Override
    public Response visit(RemoveCourseFromScheduleRequest request) {
        Schedule schedule = gateway.getSchedule(request.scheduleID);
        if (schedule == null) return Response.invalidSchedule();
        if (!matchesAdminOrUser(schedule.getOwner(), request.getSession().getUser())) return Response.invalidUsername();
        return Response.ok();
    }

    private boolean matchesAdminOrUser(User owner, User user) {
        return owner.equals(user) || user.isAdmin();
    }
}
