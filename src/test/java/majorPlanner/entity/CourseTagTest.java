package majorPlanner.entity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class CourseTagTest {

    @Test
    public void courseShouldStartWithoutTags(){
        Course course = new Course("123");
        assertThat(course.getTags().isEmpty(), is(true));
    }

}
