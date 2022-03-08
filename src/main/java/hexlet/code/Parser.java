package hexlet.code;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import java.io.IOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

    public static Map getData(String filepath) throws Exception { //getData
        if (filepath.endsWith(".json")) {
            return getDataJson(getString(filepath));
        } else if (filepath.endsWith(".yml") || filepath.endsWith(".yaml")) {
            return getDataYaml(getString(filepath));
        } else {
            throw new IllegalArgumentException("Don't know this extension!");
        }
    }

    public static String getString(String filepath) throws IOException {
        return Files.readString(Path.of(filepath));
    }

    public static Map getDataJson(String content) throws Exception {  //getDataJson
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        return mapper.readValue(content, Map.class);
    }

    public static Map getDataYaml(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(content, Map.class);
    }


    //edit method getStylish
    public static String getStylish(Map differdata) throws JsonProcessingException {
        //ObjectMapper mapper = new ObjectMapper();
        StringBuilder output = new StringBuilder("{\n");
        for (Object key: differdata.keySet()) {
            output.append("  ")
                    .append(key.toString())
                    .append(": ")
                    .append(differdata.get(key))
                    .append("\n");
        }
        return output + "}";
        //return mapper.writeValueAsString(differdata)
        //        .replaceFirst("\\{", "\\{\n  ")
        //        .replaceAll("\"", "")
        //        .replaceAll(",", ",\n  ")
        //        .replaceAll("\\}", "\n\\}");
    }
}
