package majorPlanner.entity;

import java.util.Objects;
import java.util.Set;

public class CourseRequirement implements Requirement {

    private final String courseId;

    public CourseRequirement(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public Set<Course> matches(Set<Course> courses) {
        for (Course course : courses) {
            if (course.getId().equals(courseId)){
                return Set.of(course);
            }
        }
        return Set.of();
    }

    public String getCourseId() {
        return courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRequirement that = (CourseRequirement) o;
        return Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return "course=" + courseId + '}';
    }
}
