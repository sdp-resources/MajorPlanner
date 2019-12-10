package json;

import majorPlanner.entity.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class ReadWriteTest {

    private final JsonConverter jsonConverter = new JsonConverter();

    @Test
    public void roundTrips() throws IOException {
        assertRoundTrips(tagged("ENG", "1XX"));
        assertRoundTrips(tagged());
        assertRoundTrips(course("MAT121"));
        assertRoundTrips(oneOf(tagged("ENG"), course("CS220")));
        assertRoundTrips(new ExcludeRequirement(course("CS223"), Set.of("MAT121", "CS321", "CS328")));
        assertRoundTrips(new Program("Program name", "Program description",
                                     List.of(stored("desc", course("MAT221")))));
        assertRoundTrips(Course.withTags("ENG131", "ENG", "1xx"));
    }

    @Test
    public void roundTripsPrograms() throws IOException {
        assertRoundTrips(sampleProgram());
    }

    private void assertRoundTrips(Course course) throws IOException {
        String value = jsonConverter.serialize(course);
        Course result = jsonConverter.deserializeCourse(value);
        assertThat(result, is(course));

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

    private Program sampleProgram() {
        return new Program("English Major", "a description", sampleRequirements());
    }

    private List<StoredRequirement> sampleRequirements() {
        return List.of(
                stored("lit analysis", course("ENG240")),
                stored("survey course 1", oneOf(
                        course("ENG243"), course("ENG244"),
                        course("ENG245"), course("ENG246"),
                        course("ENG247"))),
                stored("survey course 2", oneOf(
                        course("ENG243"), course("ENG244"),
                        course("ENG245"), course("ENG246"),
                        course("ENG247"))),
                stored("ENG3xx/4xx", oneOf(tagged("ENG", "3xx"),
                                           tagged("ENG", "4xx")))
        );
    }

    @NotNull
    private EitherRequirement oneOf(Requirement... reqs) {
        return new EitherRequirement(reqs);
    }

    @NotNull
    private TagRequirement tagged(String... tags) {
        return new TagRequirement(new HashSet<>(List.of(tags)));
    }

    @NotNull
    private StoredRequirement stored(String name, Requirement req) {
        return new StoredRequirement(req, name);
    }

    @NotNull
    private CourseRequirement course(String id) {
        return new CourseRequirement(id);
    }
}
