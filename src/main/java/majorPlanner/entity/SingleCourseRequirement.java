package majorPlanner.entity;

import java.util.Set;

public class SingleCourseRequirement implements Requirement {

    private final Course course;

    public SingleCourseRequirement(Course course) {
        this.course = course;
    }

    @Override
    public Set<Course> matches(Set<Course> courses) {
        if(courses.contains(course)) return Set.of(course);
        return Set.of();
    }
}
