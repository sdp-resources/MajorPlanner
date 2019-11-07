package majorPlanner.request;

import majorPlanner.entity.Term;
import majorPlanner.entity.Year;

public class AddCourseRequest extends Request {
    public String courseID;
    public int scheduleID;
    public final Term term;
    public final Year year;

    public AddCourseRequest(String courseID, int scheduleId, Term term, Year year) {
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
