package majorPlanner.request;

public class CreateAddCourseToSchedueRequest {
    public String courseID;
    public int scheduleID;

    public CreateAddCourseToSchedueRequest(String courseID, int scheduleId) {
        this.courseID = courseID;
        this.scheduleID = scheduleId;
    }
}
