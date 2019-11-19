package majorPlanner.entity;

import java.util.HashSet;
import java.util.Set;

public class EitherCourseRequirement implements Requirement {
    private Requirement[] reqs;

    public EitherCourseRequirement(Requirement... reqs){
        this.reqs = reqs;
    }

    public Set<Course> matches(Set<Course> courses) {
        Set<Course> matchedCourses = new HashSet<>();
        for (Requirement req : reqs) {
            matchedCourses.addAll(req.matches(courses));
        }
        return matchedCourses;
    }
}
