package majorPlanner.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Program {
    private String name;
    private String description;
    private List<StoredRequirement> storedReqs;

    public Program(String name, String description, List<StoredRequirement> storedReqs) {
        this.name = name;
        this.description = description;
        this.storedReqs = storedReqs;
    }

    public List<MatchResult> match(Set<Course> courses) {
        return new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StoredRequirement> getStoredReqs() {
        return storedReqs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equals(name, program.name) &&
                Objects.equals(description, program.description) &&
                Objects.equals(storedReqs, program.storedReqs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, storedReqs);
    }

    @Override
    public String toString() {
        return "Program{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", storedReqs=" + storedReqs +
                '}';
    }
}

