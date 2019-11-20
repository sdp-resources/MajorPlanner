package majorPlanner;

import majorPlanner.entity.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequirementTests {
    private Course course_LA;
    private Course course_HS;
    private Course course_W1_LA;
    private Course course2_HS;
    private Course course2_LA_W1;

    @Before
    public void setUp() {
        course_LA = new Course("course1");
        course_HS = new Course("course2");
        course_W1_LA = new Course("course3");
        course2_HS = new Course("course4");
        course2_LA_W1 = new Course("course5");

        course_LA.addTag("LA");
        course_HS.addTag("HS");
        course_W1_LA.addTags("W1", "LA");
        course2_HS.addTag("HS");
        course2_LA_W1.addTags("LA", "W1");
    }

    @Test
    public void whenCourseListIncludesExpectedCourse_SingleCourseRequirementSucceeds() {
        assertRequirementMatchesExpected(single(course_LA), Set.of(course_LA, course_HS, course_W1_LA), Set.of(course_LA));
        assertRequirementMatchesExpected(single(course_LA), Set.of(course_HS, course_W1_LA), Set.of());
    }

    @Test
    public void whenProvidedACourseList_EitherCourseRequirementHandlesAllCases(){
        assertRequirementMatchesExpected(either(single(course_LA), single(course_HS)), Set.of(course_LA, course_HS, course_W1_LA), Set.of(course_LA, course_HS));
        assertRequirementMatchesExpected(either(single(course_LA), single(course2_LA_W1)), Set.of(course_LA, course_HS, course_W1_LA), Set.of(course_LA));
        assertRequirementMatchesExpected(either(single(course_W1_LA), single(course2_HS)), Set.of(course_LA, course_HS, course2_LA_W1), Set.of());
    }

    @Test
    public void whenProvidedCourseListContainsExcludedCourses_ExcludedCourseRequirementMatchesAllRequirementsNotUsingExcludedCourses(){
        assertRequirementMatchesExpected(excluded(either(single(course_LA), single(course_HS), single(course_W1_LA)), Set.of(course_W1_LA.getId())),
                                        Set.of(course_LA, course_HS, course_W1_LA, course2_HS), Set.of(course_LA, course_HS));
        assertRequirementMatchesExpected(excluded(either(single(course_LA), single(course_HS), single(course_W1_LA)), Set.of(course_LA.getId(), course_W1_LA.getId())),
                                        Set.of(course_LA, course_HS, course_W1_LA, course2_HS), Set.of(course_HS));
    }

    @Test
    public void whenProvidedCourseList_TagCourseRequirementMatchesCoursesWithThoseTags(){
        assertRequirementMatchesExpected(tags(Set.of("LA")), Set.of(course_LA, course_HS, course_W1_LA), Set.of(course_LA, course_W1_LA));
        assertRequirementMatchesExpected(tags(Set.of("LA", "W1")), Set.of(course_LA, course_HS, course_W1_LA), Set.of(course_W1_LA));
    }

    @Test
    public void whenProvideCourseListContainsExcludedCourse_ExcludedCourseRequirementMatchesNonExcludedCoursesWithCorrectTags(){
        assertRequirementMatchesExpected(excluded(tags(Set.of("HS")), Set.of(course2_HS.getId())), Set.of(course_LA, course_HS, course_W1_LA, course2_HS), Set.of(course_HS));
        assertRequirementMatchesExpected(excluded(tags(Set.of("LA", "W1")), Set.of(course2_LA_W1.getId())), Set.of(course_LA, course_W1_LA, course2_LA_W1), Set.of(course_W1_LA));
    }

    private void assertRequirementMatchesExpected(Requirement req, Set<Course> provided, Set<Course> expected) {
        assertThat(req.matches(provided), is(expected));
    }

    @NotNull
    private TagRequirement tags(Set<String> tags) { return new TagRequirement(tags); }

    @NotNull
    private ExcludedCourseRequirement excluded(Requirement req, Set<String> excludedCourseIdList) { return new ExcludedCourseRequirement(req, excludedCourseIdList); }

    @NotNull
    private CourseRequirement single(Course course) {
        return new CourseRequirement(course.getId());
    }

    @NotNull
    private EitherRequirement either(Requirement... reqs) {
        return new EitherRequirement(reqs);
    }

}

