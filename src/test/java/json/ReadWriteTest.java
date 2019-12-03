package json;

import majorPlanner.entity.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class ReadWriteTest {

    private final JsonConverter jsonConverter = new JsonConverter();

    @Test
    public void roundTrips() throws IOException {
        assertRoundTrips(new TagRequirement(Set.of("ENG", "1XX")));
        assertRoundTrips(new TagRequirement(Set.of()));
        assertRoundTrips(new CourseRequirement("MAT121"));
        assertRoundTrips(new EitherRequirement(
                new TagRequirement(Set.of("ENG")),
                new CourseRequirement("CS220")));
        assertRoundTrips(new ExcludeRequirement(new CourseRequirement("CS223"), Set.of("MAT121", "CS321", "CS328")));
        assertRoundTrips(new Program("Program name", "Program description",
                List.of(new StoredRequirement(new CourseRequirement("MAT221"), "desc"))));
    }

    private void assertRoundTrips(Requirement requirement) throws IOException {
        String value = jsonConverter.serialize(requirement);
        Requirement result = jsonConverter.deserializeRequirement(value);
        assertThat(result, is(requirement));
    }

    private void assertRoundTrips(Program program) throws IOException {
        String value = jsonConverter.serialize(program);
        Program result = jsonConverter.deserializeProgram(value);
        assertThat(result, is(program));
    }
}
