package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {
    public static String fileToString(String filepath) throws IOException {
        Path filename = Path.of(filepath);
        return Files.readString(filename);
    }

    public static Map getData(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    public static String getStylish(Map differdata) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(differdata)
                .replaceFirst("\\{", "\\{\n  ")
                .replaceAll("\"", "")
                .replaceAll(",", ",\n  ")
                .replaceAll("\\}", "\n\\}");
    }
}
