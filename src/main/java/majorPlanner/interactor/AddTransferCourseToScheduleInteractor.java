package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.AddTransferCourseToScheduleRequest;
import majorPlanner.response.Response;

public class AddTransferCourseToScheduleInteractor implements Interactor {

    private AddTransferCourseToScheduleRequest request;
    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;

    public AddTransferCourseToScheduleInteractor(AddTransferCourseToScheduleRequest request, CourseGateway courseGateway, ScheduleGateway scheduleGateway) {
        this.request = request;
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    @Override
    public Response execute() {
        Course course = courseGateway.getCourse(request.courseId);
        Schedule schedule = scheduleGateway.getSchedule(request.scheduleId);
        if (course == null){ return Response.invalidCourse(); }
        if (schedule == null) { return Response.invalidSchedule(); }
        if (schedule.containsCourse(course)) { return Response.previouslyAddedCourse(); }
        schedule.addTransferCourse(course);
        scheduleGateway.save();
        return Response.success(schedule);
    }
}
