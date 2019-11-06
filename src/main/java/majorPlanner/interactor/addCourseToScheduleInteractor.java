package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.ScheduleGateway;
import majorPlanner.request.AddCourseRequest;
import majorPlanner.request.Request;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;

public class addCourseToScheduleInteractor implements Interactor {

    private final CourseGateway courseGateway;
    private final ScheduleGateway scheduleGateway;

    public addCourseToScheduleInteractor(CourseGateway courseGateway, ScheduleGateway scheduleGateway){
        this.courseGateway = courseGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public Response executeRequest(AddCourseRequest request) {
        Course course = courseGateway.getCourse(request.courseID);
        Schedule schedule = scheduleGateway.getSchedule(request.scheduleID);
        if (course == null) return ErrorResponse.invalidCourse();
        if (schedule == null) return ErrorResponse.invalidSchedule();
        if (schedule.containsCourse(course)) return ErrorResponse.previouslyAddedCourse();
        schedule.addCourse(course, request.term, request.year);
        scheduleGateway.save();
        return new SuccessResponse<>(schedule);
    }

    @Override
    public Response execute(Request request) {
        return executeRequest((AddCourseRequest) request);
    }
}
