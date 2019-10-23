package majorPlanner.request;

public class CreateScheduleRequest implements Request {
    public String ownerID;

    public CreateScheduleRequest(String ownerID) {
        this.ownerID = ownerID;
    }
}
