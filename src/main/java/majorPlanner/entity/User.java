package majorPlanner.entity;

public class User {
    private String userID;
    private Role role;

    public User(String userID, Role role) {
        this.userID = userID;
        this.role = role;
    }

    public User(String userID)
    {
        this(userID, Role.User);
    }

    public String getUserID() {
        return userID;
    }

    public Role getRole() { return role; }

}
