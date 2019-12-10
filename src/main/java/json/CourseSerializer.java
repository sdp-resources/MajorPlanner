package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import majorPlanner.entity.Course;

import java.io.IOException;

public class CourseSerializer extends StdSerializer<Course> {
    protected CourseSerializer(Class<Course> t) {
        super(t);
    }

    @Override
    public void serialize(Course course, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", course.getId());
        jsonGenerator.writeFieldName("tags");
        jsonGenerator.writeObject(course.getTags());
        jsonGenerator.writeEndObject();
    }
}
