package majorPlanner.gateway;

import majorPlanner.entity.User;

public interface UserGateway {
    User getUser(String userID);
    void addUser(User user);
}
