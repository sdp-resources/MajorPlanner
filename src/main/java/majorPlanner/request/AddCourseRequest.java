package majorPlanner.request;

public class AddCourseRequest extends Request {
    public final String term;
    public final String year;
    public String courseID;
    public int scheduleID;

    public AddCourseRequest(String courseID, int scheduleId, String term, String year) {
        this.courseID = courseID;
        this.scheduleID = scheduleId;
        this.term = term;
        this.year = year;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
