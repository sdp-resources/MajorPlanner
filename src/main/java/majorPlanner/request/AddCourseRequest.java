package majorPlanner.request;

public class AddCourseRequest extends Request {
    public final String period;
    public final String year;
    public String courseId;
    public int scheduleId;

    public AddCourseRequest(String courseId, int scheduleId, String period, String year) {
        this.courseId = courseId;
        this.scheduleId = scheduleId;
        this.period = period;
        this.year = year;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
