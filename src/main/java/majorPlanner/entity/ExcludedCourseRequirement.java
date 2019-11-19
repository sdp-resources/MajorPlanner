package majorPlanner.entity;

import java.util.HashSet;
import java.util.Set;

public class ExcludedCourseRequirement implements Requirement {

    private final Requirement req;
    private final Set<String> excludedCourseIds;

    public ExcludedCourseRequirement(Requirement req, Set<String> excludedCourseIds) {
        this.excludedCourseIds = excludedCourseIds;
        this.req = req;
    }

    public Set<Course> matches(Set<Course> courses) {
        Set<Course> matchedCourses = new HashSet<>();
        for (Course course : req.matches(courses)) {
            if (!excludedCourseIds.contains(course.getId())){
                matchedCourses.add(course);
            }
        }
        return matchedCourses;
    }
}
