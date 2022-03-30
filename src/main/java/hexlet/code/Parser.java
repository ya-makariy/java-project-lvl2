package hexlet.code;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {
    public static final String[] DATAFORMAT = {"JSON", "YAML"};

    public static Map getData(String content) {
        int i = 0;
        while (true) {
            try {
                return getDataByType(content, DATAFORMAT[i]);
            } catch (Exception e) {
                if (++i == DATAFORMAT.length) {
                    throw new IllegalArgumentException("ERROR: Don't know this data format!");
                }
            }
        }
    }

    public static Map getDataByType(String content, String type) throws Exception {
        if (type.equals("JSON")) {
            return getDataJson(content);
        } else if (type.equals("YAML")) {
            return getDataYaml(content);
        } else {
            throw new IllegalArgumentException(String.format("ERROR: Don't know '%s' data format!", type));
        }
    }

    public static String getString(String filepath) throws IOException {
        return Files.readString(Path.of(filepath));
    }

    public static Map getDataJson(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        return mapper.readValue(content, Map.class);
    }

    public static Map getDataYaml(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(content, Map.class);
    }
}
