package majorPlanner.request;

public class CompareScheduleRequest extends Request {
    private int scheduleId;

    public CompareScheduleRequest(int scheduleId) {

        this.scheduleId = scheduleId;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public int getScheduleId() {
        return scheduleId;
    }
}
