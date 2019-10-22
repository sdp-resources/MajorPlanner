package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.CreateAddCourseToSchedueRequest;
import majorPlanner.response.CreateAddCourseToScheduleResponse;

public class AddCourseToScheduleInteractor {

    public static final String INVALID_COURSE_MESSAGE = "Invalid Course";
    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;

    public AddCourseToScheduleInteractor(CourseGateway courseGateway, ScheduleGateway scheduleGateway){
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public CreateAddCourseToScheduleResponse executeRequest(CreateAddCourseToSchedueRequest request) {
        Course course = courseGateway.getCourse(request.courseID);
        return new CreateAddCourseToScheduleResponse(INVALID_COURSE_MESSAGE, request.courseID);

    }
}
