package majorPlanner.session;

import majorPlanner.entity.Role;

public class Session {
    public Session(String token, String username, Role role)
    {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String token;
    public String username;
    public Role role;
}
