package mock;

import majorPlanner.entity.User;
import majorPlanner.gateway.UserGateway;

public class AcceptingUserGateway implements UserGateway {

    private String requestedUserID;

    @Override
    public User getUser(String userID){
        requestedUserID = userID;
        return new User(userID);
    }

    @Override
    public void addUser(User user) {

    }

    public String getRequestedUserID() {
        return requestedUserID;
    }
}
