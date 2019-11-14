package majorPlanner.entity;

import java.util.Set;

public interface Requirement {
    Set<Course> matches(Set<Course> courses);

}
