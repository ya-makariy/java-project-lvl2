package hexlet.code;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class YamlTest {
    private static Map datayaml1;
    private static Map datayaml2;
    private static String resourcesPath = "src/test/resources/";
    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static PrintStream originalOut = System.out;
    private static String expectedshort = """
            {
              - follow: false
                host: hexlet.io
              - proxy: 123.234.53.22
              - timeout: 50
              + timeout: 20
              + verbose: true
            }""";
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
        datayaml1 = Parser.getData(resourcesPath + "yaml/file1.yaml");
        datayaml2 = Parser.getData(resourcesPath + "yaml/file2.yaml");
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testDifferGenerateYamlShort() throws Exception {
        LinkedHashMap<String, Object> sortedmap = Differ.sorteddata(datayaml1, datayaml2);
        String dataResult = Differ.generate(datayaml1, datayaml2, sortedmap);
        assertThat(dataResult).isEqualTo(expectedshort);
    }
    @Test
    void testDifferGenerateYamlLong() throws Exception {
        LinkedHashMap<String, Object> sortedmap = Differ.sorteddata(datayaml1, datayaml2);
        String dataResult = Differ.generate(datayaml1, datayaml2, sortedmap);
        assertThat(dataResult).isEqualTo(expectedshort);
    }

    @Test
    void testAppMainYaml() {
        String[] appArgs = {resourcesPath + "yaml/longyaml1.yml", resourcesPath + "yaml/longyaml2.yml"};
        App.main(appArgs);
        assertThat(outContent.toString()).isEqualTo(expectedlong);
    }
}
