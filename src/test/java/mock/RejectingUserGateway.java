package mock;

import majorPlanner.entity.User;
import majorPlanner.gateway.UserGateway;

public class RejectingUserGateway implements UserGateway {
    private String requestedUserID = null;

    @Override
    public User getUser(String userID){
        requestedUserID = userID;
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    public String getRequestedUserID() {
        return requestedUserID;
    }
}
