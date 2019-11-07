package majorPlanner.request;

public class CreateScheduleRequest extends Request {
    public final String ownerID;
    public final String description;
    public final String name;

    public CreateScheduleRequest(String ownerID, String name, String description) {
        this.ownerID = ownerID;
        this.description = description;
        this.name = name;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
