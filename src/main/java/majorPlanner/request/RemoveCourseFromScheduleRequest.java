package majorPlanner.request;

public class RemoveCourseFromScheduleRequest extends Request{

    public final String courseID;
    public final int scheduleID;

    public RemoveCourseFromScheduleRequest(String courseID, int scheduleID) {

        this.courseID = courseID;
        this.scheduleID = scheduleID;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return null;
    }
}
