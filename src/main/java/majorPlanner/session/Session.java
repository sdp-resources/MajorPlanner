package majorPlanner.session;

public class Session {
    public Session(String token, String username, String role)
    {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String token;
    public String username;
    public String role;
}
