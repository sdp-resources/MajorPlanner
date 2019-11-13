package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.AddTransferCourseToScheduleRequest;
import majorPlanner.response.Response;

public class AddTransferCourseToScheduleInteractor {

    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;

    public AddTransferCourseToScheduleInteractor(CourseGateway courseGateway, ScheduleGateway scheduleGateway) {
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public Response executeRequest(AddTransferCourseToScheduleRequest request) {
        Course course = courseGateway.getCourse(request.courseId);
        Schedule schedule = scheduleGateway.getSchedule(request.scheduleId);
        if (course == null){ return Response.invalidCourse(); }
        if (schedule == null) { return Response.invalidSchedule(); }
        return Response.ok();
    }
}
