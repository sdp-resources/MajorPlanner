package majorPlanner.request;

public class CreateScheduleRequest extends Request {
    public String ownerID;

    public CreateScheduleRequest(String ownerID) {
        this.ownerID = ownerID;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return visitor.visitCreateScheduleRequest(this);
    }
}
