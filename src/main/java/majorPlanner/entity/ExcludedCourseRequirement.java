package majorPlanner.entity;

import java.util.HashSet;
import java.util.Set;

public class ExcludedCourseRequirement implements Requirement {

    private final Requirement req;
    private final Set<Course> excludedCourses;

    public ExcludedCourseRequirement(Requirement req, Set<Course> excludedCourses) {
        this.excludedCourses = excludedCourses;
        this.req = req;
    }

    public Set<Course> matches(Set<Course> courses) {
        Set<Course> matchedCourses = new HashSet<>(req.matches(courses));
        matchedCourses.removeAll(excludedCourses);
        return matchedCourses;
    }
}
