package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import majorPlanner.entity.TagRequirement;

import java.io.IOException;

public class TagRequirementSerializer extends StdSerializer<TagRequirement> {
    protected TagRequirementSerializer(Class<TagRequirement> t) {
        super(t);
    }

    @Override
    public void serialize(TagRequirement req, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", "tag");
        jsonGenerator.writeFieldName("tags");
        jsonGenerator.writeObject(req.getTags());
        jsonGenerator.writeEndObject();
    }
}
