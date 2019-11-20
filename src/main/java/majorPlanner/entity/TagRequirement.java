package majorPlanner.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TagRequirement implements Requirement{

    private final Set<String> tags;

    public TagRequirement(Set<String> tags) {
        this.tags = tags;
    }

    public Set<Course> matches(Set<Course> courses) {
        Set<Course> matchedCourses = new HashSet<>();
        for (Course course : courses) {
            if(course.hasAllTags(tags)) {
                matchedCourses.add(course);
            }
        }
        return matchedCourses;
    }

    public Set<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagRequirement that = (TagRequirement) o;
        return Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags);
    }
}
