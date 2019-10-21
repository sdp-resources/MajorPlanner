package majorPlanner.gateway;

import majorPlanner.entity.User;

public abstract class UserGateway {
    public abstract User getUser(String userID);
}
