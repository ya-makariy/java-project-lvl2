package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;


import static hexlet.code.Differ.KEYLENGTH;

public class JsonFormater {
    public static String generateJson(Map data1, Map data2, Map<String, Object> diff) throws JsonProcessingException {
        LinkedHashMap<String, Object> toJson = new LinkedHashMap<>();
        for (String key: diff.keySet()) {
            String oKey = key.substring(KEYLENGTH);
            switch (key.substring(0, KEYLENGTH)) {
                case("DEL"):
                    toJson.put("DEL: " + oKey, data1.get(oKey));
                    break;
                case("ADD"):
                    toJson.put("ADD: " + oKey, data2.get(oKey));
                    break;
                case ("CHV"):
                    toJson.put("CH_FROM: " + oKey, data1.get(oKey));
                    toJson.put("CH_TO: " + oKey, data2.get(oKey));
                    break;
                default:
                    toJson.put("NOT_CH: " + oKey, data1.get(oKey));
                    break;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(toJson);
    }
}
