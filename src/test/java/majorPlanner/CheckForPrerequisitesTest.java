package majorPlanner;


import majorPlanner.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CheckForPrerequisitesTest {

    private User owner;
    private String name = "Me";
    private String description = "Prereq";
    private String id = "MAT120";
    private String id2 = "MAT121";
    private Schedule schedule;
    private Course course1;
    private Course course2;
    private String period = "Fall";
    private String year = "Freshman";
    private String year2 = "Junior";

    @Before
    public void setUp() throws Exception {
        schedule = new Schedule(owner, name, description);
        course1 = new Course(id);
        course2 = new Course(id2);
    }

    @Test
    public void whenScheduleHasCourseWithNoPrerequisite_NoProblems() {
        schedule.addCourse(course1, period, year);
        schedule.addCourse(course2, period, year2);
        List<PrerequisiteProblem> problems = schedule.checkPrerequisites();
        assertThat(problems.size(), is(0));
    }

    @Test
    public void whenScheduleHasCourseWithOnePrerequisite_ThereAreProblems() {
        schedule.addCourse(course1, period, year2);
        schedule.addCourse(course2, period, year);
        Prerequisite prereq = new SingleCoursePrerequisite(id);
        course2.setPrerequisite(prereq);
        List<PrerequisiteProblem> problems = schedule.checkPrerequisites();
        assertThat(problems.size(), is(1));
    }
}



