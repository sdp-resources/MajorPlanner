package majorPlanner.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EitherRequirement implements Requirement {
    private Requirement[] requirements;

    public EitherRequirement(Requirement... requirements){
        this.requirements = requirements;
    }

    public Set<Course> matches(Set<Course> courses) {
        Set<Course> matchedCourses = new HashSet<>();
        for (Requirement req : requirements) {
            matchedCourses.addAll(req.matches(courses));
        }
        return matchedCourses;
    }

    public Requirement[] getRequirements() {
        return requirements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EitherRequirement that = (EitherRequirement) o;
        return Arrays.equals(requirements, that.requirements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(requirements);
    }
}
