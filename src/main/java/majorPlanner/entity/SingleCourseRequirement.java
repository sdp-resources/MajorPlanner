package majorPlanner.entity;

import java.util.Set;

public class SingleCourseRequirement implements Requirement {

    private final String courseId;

    public SingleCourseRequirement(String courseId) {
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
}
