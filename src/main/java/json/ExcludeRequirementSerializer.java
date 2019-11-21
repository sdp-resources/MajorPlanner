package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import majorPlanner.entity.ExcludeRequirement;

import java.io.IOException;

public class ExcludeRequirementSerializer extends StdSerializer<ExcludeRequirement> {
    protected ExcludeRequirementSerializer(Class<ExcludeRequirement> t) {
        super(t);
    }

    @Override
    public void serialize(ExcludeRequirement requirement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", "exclude");
        jsonGenerator.writeFieldName("requirement");
        jsonGenerator.writeObject(requirement.getRequirement());
        jsonGenerator.writeFieldName("courses");
        jsonGenerator.writeObject(requirement.getCourses());
        jsonGenerator.writeEndObject();
    }
}
