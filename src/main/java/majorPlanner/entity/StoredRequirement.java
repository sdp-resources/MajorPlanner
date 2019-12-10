package majorPlanner.entity;

import java.util.Objects;

public class StoredRequirement {
    private Requirement requirement;
    private Integer id;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoredRequirement that = (StoredRequirement) o;
        return Objects.equals(requirement, that.requirement) &&
                Objects.equals(id, that.id) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirement, id, description);
    }

    private StoredRequirement() {}

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

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "stored{" +
                "descr='" + description + '\'' +
                ", req=" + requirement +
                '}';
    }
}
