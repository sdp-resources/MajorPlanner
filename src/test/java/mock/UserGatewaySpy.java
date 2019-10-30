package mock;

import majorPlanner.entity.User;
import majorPlanner.gateway.UserGateway;

public class UserGatewaySpy implements UserGateway {
    private String requestedUserID;

    public String getRequestedUserID() {
        return requestedUserID;
    }

    void setRequestedUserID(String requestedUserID) {
        this.requestedUserID = requestedUserID;
    }

    @Override
    public User getUser(String userID) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }
}
