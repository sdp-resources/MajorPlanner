package majorPlanner.gateway;

import majorPlanner.entity.User;

public class RejectingUserGateway extends UserGateway{
    private String requestedUserID = null;

    @Override
    public User getUser(String userID){
        requestedUserID = userID;
        return null;
    }

    public String getRequestedUserID() {
        return requestedUserID;
    }
}
