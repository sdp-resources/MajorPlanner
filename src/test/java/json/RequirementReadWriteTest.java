package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import majorPlanner.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class RequirementReadWriteTest {

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
    }

    private void assertRoundTrips(Requirement requirement) throws IOException {
        String value = jsonConverter.serialize(requirement);
        Requirement result = jsonConverter.deserialize(value);
        assertThat(result, is(requirement));
    }
}
