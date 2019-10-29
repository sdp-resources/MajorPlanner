package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.CreateAddCourseToScheduleRequest;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;

public class AddCourseToScheduleInteractor {



    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;

    public AddCourseToScheduleInteractor(CourseGateway courseGateway, ScheduleGateway scheduleGateway){
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public Response executeRequest(CreateAddCourseToScheduleRequest request) {
        Course course = courseGateway.getCourse(request.courseID);
        if (course == null) {
            return ErrorResponse.invalidCourse();
        }
        Schedule schedule = scheduleGateway.getSchedule (request.scheduleID);
        if (schedule == null){
            return ErrorResponse.invalidSchedule();
        }
        else{
            return new SuccessResponse<Schedule>(schedule);
        }


    }
}
