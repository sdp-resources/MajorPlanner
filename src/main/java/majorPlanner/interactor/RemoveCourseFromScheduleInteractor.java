package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.RemoveCourseFromScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.response.Response;


public class RemoveCourseFromScheduleInteractor implements Interactor {
    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;
    private Course courseToBeRemoved;
    private Schedule schedule;

    public RemoveCourseFromScheduleInteractor(CourseGateway courseGateway, ScheduleGateway scheduleGateway) {
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public Response executeRequest(RemoveCourseFromScheduleRequest request) {
        courseToBeRemoved = courseGateway.getCourse(request.courseID);
        schedule = scheduleGateway.getSchedule(request.scheduleID);
        if (isInvalidCourse()) return Response.invalidCourse();
        if (isInvalidSchedule()) return Response.invalidSchedule();
        if (schedule.isEmpty()) return Response.emptySchedule();
        if (!schedule.containsCourse(courseToBeRemoved)) return Response.courseNotInSchedule();
        schedule.deleteCourse(courseToBeRemoved);
        scheduleGateway.save();
        return Response.success(schedule);
    }

    private boolean isInvalidSchedule() {
        return schedule == null;
    }

    private boolean isInvalidCourse() {
        return courseToBeRemoved == null;
    }

    @Override
    public Response execute(Request request) {
        return executeRequest((RemoveCourseFromScheduleRequest) request);
    }
}