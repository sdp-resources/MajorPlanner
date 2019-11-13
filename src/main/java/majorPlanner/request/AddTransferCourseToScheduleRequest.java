package majorPlanner.request;


import majorPlanner.entity.Term;
import majorPlanner.entity.TransferTerm;

public class AddTransferCourseToScheduleRequest extends Request {
    public String courseId;
    public int scheduleId;
    public Term transferTerm;

    public AddTransferCourseToScheduleRequest(String courseId, int scheduleId, Term transferTerm) {
        this.courseId = courseId;
        this.scheduleId = scheduleId;
        this.transferTerm = transferTerm;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return null;
    }
}
