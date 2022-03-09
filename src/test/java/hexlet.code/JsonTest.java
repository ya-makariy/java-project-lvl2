package hexlet.code;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;


class JsonTest {

    private static Map datajson1;
    private static Map datajson2;

    private static String resourcesPath = "src/test/resources/";
    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static PrintStream originalOut = System.out;
    private static String expected = "{\n"
            + "  - follow: false\n"
            + "    host: hexlet.io\n"
            + "  - proxy: 123.234.53.22\n"
            + "  - timeout: 50\n"
            + "  + timeout: 20\n"
            + "  + verbose: true\n" + "}";
    private static String expectedlong = """
            {
                chars1: [a, b, c]
              - chars2: [d, e, f]
              + chars2: false
              - checked: false
              + checked: true
              - default: null
              + default: [value1, value2]
              - id: 45
              + id: null
              - key1: value1
              + key2: value2
                numbers1: [1, 2, 3, 4]
              - numbers2: [2, 3, 4, 5]
              + numbers2: [22, 33, 44, 55]
              - numbers3: [3, 4, 5]
              + numbers4: [4, 5, 6]
              + obj1: {nestedKey=value, isNested=true}
              - setting1: Some value
              + setting1: Another value
              - setting2: 200
              + setting2: 300
              - setting3: true
              + setting3: none
            }
            """;


    @BeforeAll
    public static void beforeAll() throws Exception {
        datajson1 = Parser.getData(resourcesPath + "json/file1.json");
        datajson2 = Parser.getData(resourcesPath + "json/file2.json");

        System.setOut(new PrintStream(outContent));
    }
    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testDifferGenerateJson() throws Exception {
        LinkedHashMap<String, Object> sortedmap = Differ.sorteddata(datajson1, datajson2);
        String dataResult = Differ.generate(datajson1, datajson2, sortedmap);

        assertThat(dataResult).isEqualTo(expected);
    }

    @Test
    void testAppMainJson() {
        String[] appArgs = {resourcesPath + "json/longjson1.json", resourcesPath + "json/longjson2.json"};
        App.main(appArgs);
        assertThat(outContent.toString()).isEqualTo(expectedlong);
    }



}
