package majorPlanner.request;

public class AddTransferCourseToScheduleRequest extends Request {
    public String courseId;
    public int scheduleId;

    public AddTransferCourseToScheduleRequest(String courseId, int scheduleId) {
        this.courseId = courseId;
        this.scheduleId = scheduleId;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
