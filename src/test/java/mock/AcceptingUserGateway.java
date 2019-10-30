package mock;

import majorPlanner.entity.User;

public class AcceptingUserGateway extends UserGatewaySpy {

    @Override
    public User getUser(String userID){
        setRequestedUserID(userID);
        return new User(userID);
    }
}
