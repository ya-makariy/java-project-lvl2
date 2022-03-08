package hexlet.code;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

    public static Map getData(String filepath) throws Exception { //getData
        Path filename = Path.of(filepath);
        //System.out.println(Files.readString(filename));
        if (filepath.endsWith(".json")) {
            return getDataJson(Files.readString(filename));
            //return Files.readString(filename);
        } else if (filepath.endsWith(".yml") || filepath.endsWith(".yaml")) {
            return getDataYaml(Files.readString(filename));
        } else {
            throw new IllegalArgumentException("Don't know this extension!");
        }
    }

    public static Map getDataJson(String content) throws Exception {  //getDataJson
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        System.out.println(mapper.readValue(content, Map.class).toString());
        return mapper.readValue(content, Map.class);
    }

    public static Map getDataYaml(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
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
