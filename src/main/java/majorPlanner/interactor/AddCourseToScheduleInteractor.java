package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.AddCourseRequest;
import majorPlanner.response.Response;

public class AddCourseToScheduleInteractor implements Interactor {

    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;
    private AddCourseRequest request;

    public AddCourseToScheduleInteractor(
            AddCourseRequest request,
            CourseGateway courseGateway,
            ScheduleGateway scheduleGateway){
        this.request = request;
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    @Override
    public Response execute() {
        Course course = courseGateway.getCourse(request.courseId);
        Schedule schedule = scheduleGateway.getSchedule(request.scheduleId);
        if (course == null) return Response.invalidCourse();
        if (schedule == null) return Response.invalidSchedule();
        if (schedule.containsCourse(course)) return Response.previouslyAddedCourse();
        schedule.addCourse(course, request.period, request.year);
        scheduleGateway.save();
        return Response.success(schedule);
    }
}
