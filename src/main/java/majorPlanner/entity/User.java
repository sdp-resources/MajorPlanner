package majorPlanner.entity;

import java.util.Objects;

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

    public boolean isAdmin() {
        return role.equals(Role.Admin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, role);
    }
}
