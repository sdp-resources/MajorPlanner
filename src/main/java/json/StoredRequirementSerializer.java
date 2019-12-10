package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import majorPlanner.entity.StoredRequirement;

import java.io.IOException;

public class StoredRequirementSerializer extends StdSerializer<StoredRequirement> {
    protected StoredRequirementSerializer(Class<StoredRequirement> t) {
        super(t);
    }

    @Override
    public void serialize(StoredRequirement req, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("description", req.getDescription());
        jsonGenerator.writeObjectField("requirement", req.getRequirement());
        jsonGenerator.writeObjectField("id", req.getId());
        jsonGenerator.writeEndObject();
    }
}
