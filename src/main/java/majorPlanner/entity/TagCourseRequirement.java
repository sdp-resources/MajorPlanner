package majorPlanner.entity;

import java.util.HashSet;
import java.util.Set;

public class TagCourseRequirement implements Requirement{

    private final Set<String> tags;

    public TagCourseRequirement(Set<String> tags) {
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
}
