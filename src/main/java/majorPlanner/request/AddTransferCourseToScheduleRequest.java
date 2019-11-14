package majorPlanner.request;


import majorPlanner.entity.Term;
import majorPlanner.entity.TransferTerm;

public class AddTransferCourseToScheduleRequest extends Request {
    public String courseId;
    public int scheduleId;

    public AddTransferCourseToScheduleRequest(String courseId, int scheduleId) {
        this.courseId = courseId;
        this.scheduleId = scheduleId;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return null;
    }
}
