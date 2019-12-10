package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import majorPlanner.entity.Course;

import java.io.IOException;


public class CourseDeserializer extends JsonDeserializer<Course> {
    @Override
    public Course deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = new ObjectMapper().readTree(parser);
        Course course = new Course(node.get("id").asText());
        for (JsonNode el : node.withArray("tags")) {
            course.addTag(el.asText());
        }

        return course;
    }
}
