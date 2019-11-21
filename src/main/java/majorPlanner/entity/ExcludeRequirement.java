package majorPlanner.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ExcludeRequirement implements Requirement {

    private final Requirement requirement;
    private final Set<String> courses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcludeRequirement that = (ExcludeRequirement) o;
        return Objects.equals(requirement, that.requirement) &&
                Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirement, courses);
    }

    public ExcludeRequirement(Requirement req, Set<String> courses) {
        this.courses = courses;
        this.requirement = req;
    }

    public Set<Course> matches(Set<Course> courses) {
        Set<Course> matchedCourses = new HashSet<>();
        for (Course course : requirement.matches(courses)) {
            if (!this.courses.contains(course.getId())){
                matchedCourses.add(course);
            }
        }
        return matchedCourses;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public Set<String> getCourses() {
        return courses;
    }
}
