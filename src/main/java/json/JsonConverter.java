package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import majorPlanner.entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public String serialize(Requirement requirement) throws JsonProcessingException {
        return objectMapper.writeValueAsString(requirement);
    }

    public Requirement deserialize(String json) throws IOException {
        return deserialize(objectMapper.readValue(json, JsonNode.class));
    }

    private Requirement deserialize(JsonNode json) {
        String type = json.get("type").asText();
        switch (type) {
            case "tag":
                return new TagRequirement(asStringSet(json.get("tags")));
            case "course":
                return new CourseRequirement(json.get("course").asText());
            case "either":
                return new EitherRequirement(asRequirementArray(json.get("requirements")));
            case "exclude":
                return new ExcludeRequirement(deserialize(json.get("requirement")), asStringSet(json.get("courses")));
            default:
                throw new RuntimeException("Unknown type: " + type);
        }
    }

    private Requirement[] asRequirementArray(JsonNode json) {
        List<Requirement> reqs = new ArrayList<Requirement>();
        for (JsonNode jsonNode : json) {
            reqs.add(deserialize(jsonNode));
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
}