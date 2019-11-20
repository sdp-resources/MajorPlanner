package majorPlanner.entity;

import majorPlanner.entity.Prerequisite;
import majorPlanner.entity.Schedule;
import majorPlanner.entity.Term;

public class SingleCoursePrerequisite implements Prerequisite {
    private String id;

    public SingleCoursePrerequisite(String id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return id;
    }

    @Override
    public boolean isSatisfied(Schedule schedule, Term term) {
        return true;
    }
}
