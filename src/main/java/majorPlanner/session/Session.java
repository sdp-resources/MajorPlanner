package majorPlanner.session;

import majorPlanner.entity.Role;
import majorPlanner.entity.User;

public class Session {
    public Session(String token, User user)
    {
        this.token = token;
        this.user = user;
    }

    public String token;
    private User user;

    public String getUsername() {
        return user.getUserID();
    }

    public Role getRole() {
        return user.getRole();
    }

    public User getUser() {
        return user;
    }
}
