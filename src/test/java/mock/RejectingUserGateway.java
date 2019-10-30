package mock;

import majorPlanner.entity.User;

public class RejectingUserGateway extends UserGatewaySpy {

    @Override
    public User getUser(String userID){
        setRequestedUserID(userID);
        return null;
    }

}
