package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import majorPlanner.entity.Program;
import majorPlanner.entity.StoredRequirement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramDeserializer extends StdDeserializer<Program> {

    private ObjectMapper objectMapper;

    protected ProgramDeserializer(Class<?> vc, ObjectMapper objectMapper) {
        super(vc);
        this.objectMapper = objectMapper;
    }

    @Override
    public Program deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = objectMapper.readTree(parser);
        String name = node.get("name").asText();
        String description = node.get("description").asText();
        List<StoredRequirement> requirements = asStoredRequirementsList(node.get("requirements"));
        return new Program(name, description, requirements);
    }

    private List<StoredRequirement> asStoredRequirementsList(JsonNode json) throws JsonProcessingException {
        List<StoredRequirement> reqs = new ArrayList<>();
        for (JsonNode node: json) {

            reqs.add(objectMapper.treeToValue(node, StoredRequirement.class));
        }
        return reqs;
    }

}
