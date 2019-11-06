package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.RemoveCourseFromScheduleRequest;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;


public class RemoveCourseFromScheduleInteractor {
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
        if (isInvalidCourse()) return ErrorResponse.invalidCourse();
        if (isInvalidSchedule()) return ErrorResponse.invalidSchedule();
        if (schedule.isEmpty()) return ErrorResponse.emptySchedule();
        if (!schedule.containsCourse(courseToBeRemoved)) return ErrorResponse.courseNotInSchedule();
        schedule.deleteCourse(courseToBeRemoved);
        scheduleGateway.save();
        return new SuccessResponse<Schedule>(schedule);
    }

    private boolean isInvalidSchedule() {
        return schedule == null;
    }

    private boolean isInvalidCourse() {
        return courseToBeRemoved == null;
    }
}
