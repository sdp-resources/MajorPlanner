package majorPlanner.entity;

public class StoredRequirement {
    private Requirement requirement;
    private Integer id;
    private String description;

    public StoredRequirement(Requirement requirement, String description) {
        this.requirement = requirement;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
