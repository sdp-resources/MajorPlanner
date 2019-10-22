package majorPlanner.response;

public class CreateAddCourseToScheduleResponse {
    public String message;
    public String courseID;

    public CreateAddCourseToScheduleResponse(String message, String courseID){
        this.message = message;
        this.courseID = courseID;

    }
}
