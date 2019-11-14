package majorPlanner.entity;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CourseTagTest {

    private static final String LA = "LA";
    private static final String W1 = "W1";
    private static final String HS = "HS";
    private Course course;

    @Before
    public void setUp(){
        course = new Course("123");
    }

    @Test
    public void courseShouldStartWithoutTags(){
        assertThat(course.getTags().isEmpty(), is(true));
    }

    @Test
    public void whenTagAdded_CourseHasAddedTags(){
        course.addTag(LA);
        assertThat(course.getTags().isEmpty(), is(false));
        assertThat(course.hasTag(LA), is(true));
    }

    @Test
    public void whenTagRemoved_CourseDoesNotHaveTag(){
        course.addTags(LA, W1);
        course.removeTag(LA);
        assertThat(course.getTags().size(), is(1));
        assertThat(course.hasTag(LA), is(false));
        assertThat(course.hasTag(W1), is(true));
    }

    @Test
    public void whenMultipleTagsAdded_CourseHasMultipleTags(){
        course.addTags(LA, W1, HS);
        assertThat(course.getTags().size(), is(3));
        assertThat(course.hasTag(LA), is(true));
        assertThat(course.hasTag(W1), is(true));
        assertThat(course.hasTag(HS), is(true));
    }
}
