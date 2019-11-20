package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import majorPlanner.entity.CourseRequirement;

import java.io.IOException;

class CourseRequirementSerializer extends StdSerializer<CourseRequirement> {
    protected CourseRequirementSerializer(Class<CourseRequirement> t) {
        super(t);
    }

    @Override
    public void serialize(CourseRequirement requirement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", "course");
        jsonGenerator.writeStringField("course", requirement.getCourseId());
        jsonGenerator.writeEndObject();
    }
}
