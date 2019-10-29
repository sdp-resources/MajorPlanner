package majorPlanner.entity;

public class Schedule {
    private User owner;
    private String description;
    private String name;

    public Schedule(User owner, String name, String description) {
        this.setOwner(owner);
        this.setDescription(description);
        this.setName(name);
    }


    public int getId() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }


}
