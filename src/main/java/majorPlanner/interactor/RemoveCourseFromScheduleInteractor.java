package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.RemoveCourseFromScheduleRequest;
import majorPlanner.response.Response;


public class RemoveCourseFromScheduleInteractor implements Interactor {
    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;
    private Course courseToBeRemoved;
    private Schedule schedule;
    private RemoveCourseFromScheduleRequest request;

    public RemoveCourseFromScheduleInteractor(
            RemoveCourseFromScheduleRequest request,
            CourseGateway courseGateway,
            ScheduleGateway scheduleGateway) {
        this.request = request;
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public Response execute() {
        courseToBeRemoved = courseGateway.getCourse(request.courseID);
        schedule = scheduleGateway.getSchedule(request.scheduleID);
        if (isInvalidCourse()) {
            return Response.invalidCourse();
        }
        if (isInvalidSchedule()) {
            return Response.invalidSchedule();
        }
        if (schedule.isEmpty()) {
            return Response.emptySchedule();
        }
        if (!schedule.containsCourse(courseToBeRemoved)) {
            return Response.courseNotInSchedule();
        }
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
}
