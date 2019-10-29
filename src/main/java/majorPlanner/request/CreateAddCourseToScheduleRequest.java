package majorPlanner.request;

import majorPlanner.entity.Term;
import majorPlanner.entity.Year;

public class CreateAddCourseToScheduleRequest {
    public String courseID;
    public int scheduleID;
    public final Term term;
    public final Year year;

    public CreateAddCourseToScheduleRequest(String courseID, int scheduleId, Term term, Year year) {
        this.courseID = courseID;
        this.scheduleID = scheduleId;
        this.term = term;
        this.year = year;
    }
}
