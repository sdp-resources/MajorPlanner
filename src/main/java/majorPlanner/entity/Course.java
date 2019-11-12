package majorPlanner.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Course {
    private final String id;
    private Set<String> tags = new HashSet<>();

    public Course (String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addTag() {

    }

    public boolean hasTag() {
        return false;
    }

    public Set<String> getTags(){
        return tags;
    }

    public void removeTag() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
