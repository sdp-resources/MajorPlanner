package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import majorPlanner.entity.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class JsonConverter {
    private ObjectMapper objectMapper;

    public JsonConverter() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("TagSerializer");
        module.addSerializer(TagRequirement.class, new TagRequirementSerializer(TagRequirement.class));
        module.addSerializer(CourseRequirement.class, new CourseRequirementSerializer(CourseRequirement.class));
        module.addSerializer(EitherRequirement.class, new EitherRequirementSerializer(EitherRequirement.class));
        module.addSerializer(ExcludeRequirement.class, new ExcludeRequirementSerializer(ExcludeRequirement.class));
        module.addSerializer(StoredRequirement.class, new StoredRequirementSerializer(StoredRequirement.class));
        module.addSerializer(Course.class, new CourseSerializer(Course.class));
        module.addDeserializer(Course.class, new CourseDeserializer());
        module.addDeserializer(Program.class, new ProgramDeserializer(Program.class, objectMapper));
        module.addDeserializer(Requirement.class, new RequirementStdDeserializer(Requirement.class));
        objectMapper.registerModule(module);
    }

    public String serialize(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    public Course deserializeCourse(String value) throws IOException {
        return objectMapper.readValue(value, Course.class);
    }

    public Program deserializeProgram(String json) throws IOException {
        return deserializeProgram(deserializeJsonNode(json));
    }

    private Program deserializeProgram(JsonNode json) {
        String name = json.get("name").asText();
        String description = json.get("description").asText();
        List<StoredRequirement> requirements = asStoredRequirementsList(json.get("storedReqs"));
        return new Program(name, description, requirements);
    }

    public Requirement deserializeRequirement(String json) throws IOException {
        return deserializeRequirement(deserializeJsonNode(json));
    }

    public List<Course> deserializeCourseList(String json) throws IOException {
        return deserializeList(deserializeArrayNodeField(json, "courses"), Course.class);
    }

    public List<Program> deserializeProgramList(String json) throws IOException {
        return deserializeList(deserializeArrayNodeField(json, "programs"), Program.class);
    }

    private JsonNode deserializeArrayNodeField(String json, String field) throws IOException {
        return deserializeJsonNode(json).withArray(field);
    }

    private JsonNode deserializeJsonNode(String json) throws IOException {
        return objectMapper.readValue(json, JsonNode.class);
    }

    @NotNull
    private <T> List<T> deserializeList(JsonNode array, Class<T> tClass) throws JsonProcessingException {
        List<T> items = new ArrayList<>();
        for (JsonNode node : array) {
            items.add(objectMapper.treeToValue(node, tClass));
        }
        return items;
    }


    private Requirement deserializeRequirement(JsonNode json) {
        String type = json.get("type").asText();
        switch (type) {
            case "tag":
                return new TagRequirement(asStringSet(json.get("tags")));
            case "course":
                return new CourseRequirement(json.get("course").asText());
            case "either":
                return new EitherRequirement(asRequirementArray(json.get("requirements")));
            case "exclude":
                return new ExcludeRequirement(deserializeRequirement(json.get("requirement")), asStringSet(json.get("courses")));
            default:
                throw new RuntimeException("Unknown type: " + type);
        }
    }

    private Requirement[] asRequirementArray(JsonNode json) {
        List<Requirement> reqs = new ArrayList<>();
        for (JsonNode jsonNode : json) {
            reqs.add(deserializeRequirement(jsonNode));
        }
        return reqs.toArray(new Requirement[0]);
    }

    private Set<String> asStringSet(JsonNode tags) {
        Set<String> set = new HashSet<>();
        for (JsonNode tag : tags) {
            set.add(tag.textValue());
        }
        return set;
    }

    private List<StoredRequirement> asStoredRequirementsList(JsonNode json) {
        List<StoredRequirement> reqs = new ArrayList<>();
        for (JsonNode node: json) {
            reqs.add(deserializeStoredRequirement(node));
        }
        return reqs;
    }

    private StoredRequirement deserializeStoredRequirement(JsonNode json) {
        return new StoredRequirement(deserializeRequirement(json.get("requirement")), json.get("description").asText());
    }

    private class RequirementStdDeserializer extends StdDeserializer<Requirement> {
        public RequirementStdDeserializer(Class<Requirement> t) {
            super(t);
        }

        @Override
        public Requirement deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jsonParser.readValueAsTree();
            return deserializeRequirement(node);
        }
    }
}