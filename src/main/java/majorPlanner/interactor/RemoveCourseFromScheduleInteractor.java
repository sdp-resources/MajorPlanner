package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.RemoveCourseFromScheduleRequest;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;

public class RemoveCourseFromScheduleInteractor {
    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;

    public RemoveCourseFromScheduleInteractor(CourseGateway courseGateway, ScheduleGateway scheduleGateway) {
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public Response executeRequest(RemoveCourseFromScheduleRequest request) {
        Course course = courseGateway.getCourse(request.courseID);
        if(course == null){
            return ErrorResponse.invalidCourse();
        }
        Schedule schedule = scheduleGateway.getSchedule(request.scheduleID);
        return ErrorResponse.invalidSchedule();
    }
}
