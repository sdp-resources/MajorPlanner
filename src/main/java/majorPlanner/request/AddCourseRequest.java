package majorPlanner.request;

public class AddCourseRequest extends Request {
    public final String period;
    public final String year;
    public String courseID;
    public int scheduleID;

    public AddCourseRequest(String courseID, int scheduleId, String period, String year) {
        this.courseID = courseID;
        this.scheduleID = scheduleId;
        this.period = period;
        this.year = year;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
