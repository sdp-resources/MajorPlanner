package majorPlanner.request;

public class ViewScheduleRequest extends Request{
    public int scheduleID;

    public ViewScheduleRequest(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return null;
    }
}
