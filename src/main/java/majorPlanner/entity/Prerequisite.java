package majorPlanner.entity;

import majorPlanner.entity.Schedule;
import majorPlanner.entity.Term;

public interface Prerequisite {
    String getMessage();
    boolean isSatisfied(Schedule schedule, Term term);
}