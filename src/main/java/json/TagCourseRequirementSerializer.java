package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import majorPlanner.entity.TagCourseRequirement;

import java.io.IOException;

public class TagCourseRequirementSerializer extends StdSerializer<TagCourseRequirement> {
    protected TagCourseRequirementSerializer(Class<TagCourseRequirement> t) {
        super(t);
    }

    @Override
    public void serialize(TagCourseRequirement req, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", "tag");
        jsonGenerator.writeFieldName("tags");
        jsonGenerator.writeObject(req.getTags());
        jsonGenerator.writeEndObject();
    }
}
