package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import majorPlanner.entity.EitherRequirement;
import majorPlanner.entity.Requirement;

import java.io.IOException;

public class EitherRequirementSerializer extends StdSerializer<EitherRequirement> {
    protected EitherRequirementSerializer(Class<EitherRequirement> t) {
        super(t);
    }

    @Override
    public void serialize(EitherRequirement requirement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", "either");
        jsonGenerator.writeFieldName("requirements");
        jsonGenerator.writeObject(requirement.getRequirements());
        jsonGenerator.writeEndObject();
    }
}
