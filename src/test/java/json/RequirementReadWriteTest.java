package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import majorPlanner.entity.TagCourseRequirement;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class RequirementReadWriteTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        TagCourseRequirementSerializer tagSerializer = new TagCourseRequirementSerializer(TagCourseRequirement.class);
        SimpleModule module = new SimpleModule("TagSerializer");
        module.addSerializer(TagCourseRequirement.class, tagSerializer);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    public void testWriteTag() throws JsonProcessingException {
        TagCourseRequirement req = new TagCourseRequirement(Set.of("ENG", "1XX"));
        String value = objectMapper.writeValueAsString(req);
        System.out.println(value);
    }
}
