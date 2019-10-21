package majorPlanner.response;

public class CreateScheduleResponse {
    public String message;
    public String ownerID;

    public CreateScheduleResponse(String message, String ownerID) {
        this.message = message;
        this.ownerID = ownerID;
    }
}
