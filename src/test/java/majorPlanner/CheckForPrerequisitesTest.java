package majorPlanner;


import majorPlanner.entity.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CheckForPrerequisitesTest {

    private User owner;
    private String name = "Me";
    private String description = "Prereq";
    private String id1 = "MAT120";
    private String id2 = "MAT121";
    private Schedule schedule;
    private Course course1;
    private Course course2;
    private String period1 = "Fall";
    private String period2 = "Winter";
    private String year1 = "Freshman";
    private String year2 = "Sophomore";

    @Before
    public void setUp() throws Exception {
        schedule = new Schedule(owner, name, description);
        course1 = new Course(id1);
        course2 = new Course(id2);
    }

    @Test
    public void whenScheduleHasCourseWithNoPrerequisite_NoProblems() {
        schedule.addCourse(course1, period1, year1);
        schedule.addCourse(course2, period1, year2);
        List<PrerequisiteProblem> problems = schedule.checkPrerequisites();
        assertThat(problems.size(), is(0));
    }

    @Test
    public void whenScheduleHasCourseWithSinglePrerequisite_ThereAreNoProblems() {
        schedule.addCourse(course1, period1, year1);
        schedule.addCourse(course2, period1, year2);
        Prerequisite prereq = new SingleCoursePrerequisite(id1);
        course2.setPrerequisite(prereq);
        List<PrerequisiteProblem> problems = schedule.checkPrerequisites();
        assertThat(problems.size(), is(0));
    }

    @Ignore
    @Test
    public void whenScheduleHasCourseWithSinglePrerequisite_ThereIsOneProblem() {
        schedule.addCourse(course2, period1, year1);
        schedule.addCourse(course1, period1, year2);
        Prerequisite prereq = new SingleCoursePrerequisite(id1);
        course2.setPrerequisite(prereq);
        List<PrerequisiteProblem> problems = schedule.checkPrerequisites();
        assertThat(problems.size(), is(1));
    }

}





