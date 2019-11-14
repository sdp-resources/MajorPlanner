package majorPlanner;

import majorPlanner.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequirementTests {
    private Course course1;
    private Course course2;
    private Course course3;
    private SingleCourseRequirement singleCourseRequirement;

    @Before
    public void setUp() throws Exception {
        course1 = new Course("course1");
        course2 = new Course("course2");
        course3 = new Course("course3");
        singleCourseRequirement = new SingleCourseRequirement(course1);
    }

    @Test
    public void whenCourseListIncludesExpectedCourse_SingleCourseRequirementSucceeds() {
        assertThat(singleCourseRequirement.matches(Set.of(course1, course2, course3)), is(Set.of(course1)));
        assertThat(singleCourseRequirement.matches(Set.of(course2, course3)), is(Set.of()));
    }
}
