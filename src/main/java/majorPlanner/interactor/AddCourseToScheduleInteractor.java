package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.CreateAddCourseToSchedueRequest;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;

public class AddCourseToScheduleInteractor {

    public static final String INVALID_COURSE_MESSAGE = "Invalid Course";
    public static final String INVALID_SCHEDULE_MESSAGE = "Invalid Schedule";
    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;

    public AddCourseToScheduleInteractor(CourseGateway courseGateway, ScheduleGateway scheduleGateway){
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public Response executeRequest(CreateAddCourseToSchedueRequest request) {
        Course course = courseGateway.getCourse(request.courseID);
        if (course == null) {
            return new ErrorResponse(INVALID_COURSE_MESSAGE);
        }
        Schedule schedule = scheduleGateway.getSchedule(request.scheduleID);
        if (schedule == null){
            return new ErrorResponse(INVALID_SCHEDULE_MESSAGE);
        }
        else{
            return new SuccessResponse<Schedule>(null);
        }


    }
}
