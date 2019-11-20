package majorPlanner.entity;

public class NoPrerequisite implements Prerequisite {
    @Override
    public String getMessage() {
        return "No prerequisite";
    }

    @Override
    public boolean isSatisfied(Schedule schedule, Term term) {
        return true;
    }
}
