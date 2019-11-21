package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import majorPlanner.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class RequirementReadWriteTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        SimpleModule module = new SimpleModule("TagSerializer");
        module.addSerializer(TagRequirement.class, new TagRequirementSerializer(TagRequirement.class));
        module.addSerializer(CourseRequirement.class, new CourseRequirementSerializer(CourseRequirement.class));
        module.addSerializer(EitherRequirement.class, new EitherRequirementSerializer(EitherRequirement.class));
        module.addSerializer(ExcludeRequirement.class, new ExcludeRequirementSerializer(ExcludeRequirement.class));
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

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
        String value = serialize(requirement);
        Requirement result = deserialize(value);
        assertThat(result, is(requirement));
    }

    private String serialize(Requirement requirement) throws JsonProcessingException {
        return objectMapper.writeValueAsString(requirement);
    }

    public Requirement deserialize(String json) throws IOException {
        return deserialize(objectMapper.readValue(json, JsonNode.class));
    }

    private Requirement deserialize(JsonNode json) {
        String type = json.get("type").asText();
        switch (type) {
            case "tag":
                return readTagRequirement(json);
            case "course":
                return readCourseRequirement(json);
            case "either":
                return readEitherRequirement(json);
            case "exclude":
                return readExcludeRequirement(json);
            default:
                throw new RuntimeException("Unknown type: " + type);
        }
    }

    private Requirement readExcludeRequirement(JsonNode json) {
        return new ExcludeRequirement(deserialize(json.get("requirement")), asStringSet(json.get("courses")));
    }

    private Requirement readEitherRequirement(JsonNode json) {
        return new EitherRequirement(asRequirementArray(json.get("requirements")));
    }

    private Requirement[] asRequirementArray(JsonNode json) {
        List<Requirement> reqs = new ArrayList<>();
        for (JsonNode jsonNode : json) {
            reqs.add(deserialize(jsonNode));
        }
        return reqs.toArray(new Requirement[0]);
    }

    private Requirement readTagRequirement(JsonNode json) {
        return new TagRequirement(asStringSet(json.get("tags")));
    }

    private Requirement readCourseRequirement(JsonNode json) {
        return new CourseRequirement(json.get("course").asText());
    }

    private Set<String> asStringSet(JsonNode tags) {
        Set<String> set = new HashSet<>();
        for (JsonNode tag : tags) {
            set.add(tag.asText());
        }
        return set;
    }
}
