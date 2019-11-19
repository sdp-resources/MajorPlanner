package majorPlanner;

import majorPlanner.entity.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequirementTests {
    private Course course1;
    private Course course2;
    private Course course3;

    @Before
    public void setUp() {
        course1 = new Course("course1");
        course2 = new Course("course2");
        course3 = new Course("course3");
    }

    @Test
    public void whenCourseListIncludesExpectedCourse_SingleCourseRequirementSucceeds() {
        assertRequirementMatchesExpected(single(course1), Set.of(course1, course2, course3), Set.of(course1));
        assertRequirementMatchesExpected(single(course1), Set.of(course2, course3), Set.of());
    }

    @Test
    public void whenCourseListIncludesExpectedCourses_EitherCourseRequirementSucceeds(){
        assertRequirementMatchesExpected(either(single(course1), single(course2)), Set.of(course1, course2, course3), Set.of(course1, course2));
    }


    private void assertRequirementMatchesExpected(Requirement req, Set<Course> provided, Set<Course> expected) {
        assertThat(req.matches(provided), is(expected));
    }

    @NotNull
    private SingleCourseRequirement single(Course course) {
        return new SingleCourseRequirement(course);
    }

    @NotNull
    private EitherCourseRequirement either(Requirement... reqs) {
        return new EitherCourseRequirement(reqs);
    }

}

