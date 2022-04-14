package hexlet.code;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Parser {

    public static Map<String, Object> getData(String content, String contentType) throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper(contentType);
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

    private static ObjectMapper getObjectMapper(String type) {
        return switch (type) {
            case ("JSON") -> new ObjectMapper(new JsonFactory());
            case ("YAML") -> new ObjectMapper(new YAMLFactory());
            default -> throw new IllegalArgumentException(String.format("ERROR: Don't know '%s' data format!", type));
        };
    }
}
