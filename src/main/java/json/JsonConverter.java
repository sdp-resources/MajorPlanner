package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import majorPlanner.entity.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JsonConverter {
    private ObjectMapper objectMapper;

    public JsonConverter() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("TagSerializer");
        module.addSerializer(TagRequirement.class, new TagRequirementSerializer(TagRequirement.class));
        module.addSerializer(CourseRequirement.class, new CourseRequirementSerializer(CourseRequirement.class));
        module.addSerializer(EitherRequirement.class, new EitherRequirementSerializer(EitherRequirement.class));
        module.addSerializer(ExcludeRequirement.class, new ExcludeRequirementSerializer(ExcludeRequirement.class));
        objectMapper.registerModule(module);
    }

    public String serialize(Program program) throws JsonProcessingException {
        return objectMapper.writeValueAsString(program);
    }

    public String serialize(Requirement requirement) throws JsonProcessingException {
        return objectMapper.writeValueAsString(requirement);
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

    private JsonNode deserializeJsonNode(String json) throws IOException {
        return objectMapper.readValue(json, JsonNode.class);
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
        List<Requirement> reqs = new ArrayList<Requirement>();
        for (JsonNode jsonNode : json) {
            reqs.add(deserializeRequirement(jsonNode));
        }
        return reqs.toArray(new Requirement[0]);
    }

    private Set<String> asStringSet(JsonNode tags) {
        Set<String> set = new HashSet<String>();
        for (JsonNode tag : tags) {
            set.add(tag.asText());
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
}