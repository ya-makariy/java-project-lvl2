package hexlet.code;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Compares two configuration files and shows a difference.")
public class Differ implements Callable {

    @Parameters(index = "0", description = "path to first file")
    String filepath1;

    @Parameters(index = "1", description = "path to second file")
    String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Override
    public String call() {
        String string1;

        try {
            string1 = fileToString(filepath1);
        } catch (IOException e) {
            e.printStackTrace();
            return "error with fileToString";
        }

        String string2;

        try {
            string2 = fileToString(filepath2);
        } catch (IOException e) {
            e.printStackTrace();
            return "error with fileToString";
        }

        Map map1;
        try {
            map1 = getData(string1);
        } catch (Exception e) {
            e.printStackTrace();
            return "error with getData";
        }
        Map map2;
        try {
            map2 = getData(string2);
        } catch (Exception e) {
            e.printStackTrace();
            return "error with getData";
        }

        String data;
        try {
            data = generate(map1, map2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error with generate";
        }
        System.out.println(data);
        return "0";
    }

    // это наверно в engine позапихивать
    public String fileToString(String filepath) throws IOException {
        Path filename = Path.of(filepath);
        return Files.readString(filename);
    }

    public static Map getData (String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    public static String getStylish (Map differdata) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(differdata)
                .replaceFirst("\\{", "\\{\n  ")
                .replaceAll("\"", "")
                .replaceAll(",", ",\n  ")
                .replaceAll("\\}", "\n\\}");
    }


    public static String generate(Map datafile1, Map datafile2) throws JsonProcessingException {
        //в другой метод
        TreeMap fullData = new TreeMap<>();
        fullData.putAll(datafile1);
        fullData.putAll(datafile2);
        //return fullData.toString();

        Map<String, Object> datadata = new LinkedHashMap(fullData);

        //sorting
        Map<String, Object>  newdata = new LinkedHashMap();
        for (String key1: datadata.keySet()) {
            if (datafile1.containsKey(key1) && datafile2.containsKey(key1)) {
                if (datadata.get(key1).equals(datafile1.get(key1))) {
                    String key = "  " + key1;
                    newdata.put(key, datadata.get(key1));
                } else {
                    String mkey = "- " + key1;
                    String pkey = "+ " + key1;
                    newdata.put(mkey, datafile1.get(key1));
                    newdata.put(pkey, datadata.get(key1));
                }
            } else if (datafile1.containsKey(key1)) {
                String key = "- " + key1;
                newdata.put(key, datadata.get(key1));
            } else if (datafile2.containsKey(key1)) {
                String key = "+ " + key1;
                newdata.put(key, datadata.get(key1));
            }
        }

        return getStylish(newdata);

    }

}