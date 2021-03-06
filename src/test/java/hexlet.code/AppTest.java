package hexlet.code;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {
    private static ByteArrayOutputStream out = new ByteArrayOutputStream();
    private static ByteArrayOutputStream err = new ByteArrayOutputStream();
    private static PrintStream originalOut = System.out;
    private static PrintStream originalErr = System.err;
    private static String filepath1;
    private static String filepath2;
    private static String format;
    private static final String RESOURCESPATH = "src/test/resources/";
    private static final String EXPECTEDSTYLISHSHORT = """
            {
              - follow: false
                host: hexlet.io
              - proxy: 123.234.53.22
              - timeout: 50
              + timeout: 20
              + verbose: true
            }""";
    private static final String EXPECTEDPLAINSHORT = """
            Property 'follow' was removed
            Property 'proxy' was removed
            Property 'timeout' was updated. From 50 to 20
            Property 'verbose' was added with value: true""";
    private static final String EXPECTEDSTYLISHLONG = """
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
            }""";
    private static final String EXPECTEDPLAINLONG = """
            Property 'chars2' was updated. From [complex value] to false
            Property 'checked' was updated. From false to true
            Property 'default' was updated. From null to [complex value]
            Property 'id' was updated. From 45 to null
            Property 'key1' was removed
            Property 'key2' was added with value: 'value2'
            Property 'numbers2' was updated. From [complex value] to [complex value]
            Property 'numbers3' was removed
            Property 'numbers4' was added with value: [complex value]
            Property 'obj1' was added with value: [complex value]
            Property 'setting1' was updated. From 'Some value' to 'Another value'
            Property 'setting2' was updated. From 200 to 300
            Property 'setting3' was updated. From true to 'none'""";
    private static final String EXPECTEDJSONSHORT = "[{\"fieldName\":\"follow\",\"value2\":null,\"value1\":false,"
            + "\"status\":\"DEL\"},"
            + "{\"fieldName\":\"host\",\"value2\":\"hexlet.io\",\"value1\":\"hexlet.io\",\"status\":\"DNC\"},"
            + "{\"fieldName\":\"proxy\",\"value2\":null,\"value1\":\"123.234.53.22\",\"status\":\"DEL\"},"
            + "{\"fieldName\":\"timeout\",\"value2\":20,\"value1\":50,\"status\":\"CHV\"},"
            + "{\"fieldName\":\"verbose\",\"value2\":true,\"value1\":null,\"status\":\"ADD\"}]";
    private static final String EXPECTEDJSONLONG = "[{\"fieldName\":\"chars1\",\"value2\":[\"a\",\"b\",\"c\"],"
            + "\"value1\":[\"a\",\"b\",\"c\"],\"status\":\"DNC\"},"
            + "{\"fieldName\":\"chars2\",\"value2\":false,\"value1\":[\"d\",\"e\",\"f\"],\"status\":\"CHV\"},"
            + "{\"fieldName\":\"checked\",\"value2\":true,\"value1\":false,\"status\":\"CHV\"},"
            + "{\"fieldName\":\"default\",\"value2\":[\"value1\",\"value2\"],\"value1\":null,\"status\":\"CHV\"},"
            + "{\"fieldName\":\"id\",\"value2\":null,\"value1\":45,\"status\":\"CHV\"},"
            + "{\"fieldName\":\"key1\",\"value2\":null,\"value1\":\"value1\",\"status\":\"DEL\"},"
            + "{\"fieldName\":\"key2\",\"value2\":\"value2\",\"value1\":null,\"status\":\"ADD\"},"
            + "{\"fieldName\":\"numbers1\",\"value2\":[1,2,3,4],\"value1\":[1,2,3,4],\"status\":\"DNC\"},"
            + "{\"fieldName\":\"numbers2\",\"value2\":[22,33,44,55],\"value1\":[2,3,4,5],\"status\":\"CHV\"},"
            + "{\"fieldName\":\"numbers3\",\"value2\":null,\"value1\":[3,4,5],\"status\":\"DEL\"},"
            + "{\"fieldName\":\"numbers4\",\"value2\":[4,5,6],\"value1\":null,\"status\":\"ADD\"},"
            + "{\"fieldName\":\"obj1\",\"value2\":{\"nestedKey\":\"value\",\"isNested\":true},"
            + "\"value1\":null,\"status\":\"ADD\"},"
            + "{\"fieldName\":\"setting1\",\"value2\":\"Another value\",\"value1\":\"Some value\",\"status\":\"CHV\"},"
            + "{\"fieldName\":\"setting2\",\"value2\":300,\"value1\":200,\"status\":\"CHV\"},"
            + "{\"fieldName\":\"setting3\",\"value2\":\"none\",\"value1\":true,\"status\":\"CHV\"}]";

    @BeforeAll
    static void setStreams() {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @AfterAll
    static void restoreInitialStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testAppMainOut() {
        filepath1 = RESOURCESPATH + "yaml/file1.yaml";
        filepath2 = RESOURCESPATH + "yaml/file2.yaml";
        String[] appArgs = {"-fplain", filepath1, filepath2};
        App.main(appArgs);
        assertEquals(EXPECTEDPLAINSHORT + "\n", out.toString());
    }

    @Test
    public void testAppMainErr() {
        filepath1 = RESOURCESPATH + "json/file1.json";
        String[] appArgs = {"-fplain", filepath1};
        App.main(appArgs);
        assertTrue(err.toString().contains("Missing required parameter: '<filepath2>'"));
    }

    @Test
    void testDifferGenerateJSONStylishShort() throws Exception {
        format = "stylish";
        filepath1 = RESOURCESPATH + "json/file1.json";
        filepath2 = RESOURCESPATH + "json/file2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDSTYLISHSHORT);
    }

    @Test
    void testDifferGenerateJSONPlainShort() throws Exception {
        format = "plain";
        filepath1 = RESOURCESPATH + "json/file1.json";
        filepath2 = RESOURCESPATH + "json/file2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDPLAINSHORT);
    }

    @Test
    void testDifferGenerateYAMLStylishShort() throws Exception {
        format = "stylish";
        filepath1 = RESOURCESPATH + "yaml/file1.yaml";
        filepath2 = RESOURCESPATH + "yaml/file2.yaml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDSTYLISHSHORT);
    }

    @Test
    void testDifferGenerateYAMLPlainShort() throws Exception {
        format = "plain";
        filepath1 = RESOURCESPATH + "yaml/file1.yaml";
        filepath2 = RESOURCESPATH + "yaml/file2.yaml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDPLAINSHORT);
    }

    @Test
    void testDifferGenerateYAMLStylishLong() throws Exception {
        format = "stylish";
        filepath1 = RESOURCESPATH + "yaml/longyaml1.yml";
        filepath2 = RESOURCESPATH + "yaml/longyaml2.yml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDSTYLISHLONG);
    }

    @Test
    void testDifferGenerateYAMLPlainLong() throws Exception {
        format = "plain";
        filepath1 = RESOURCESPATH + "yaml/longyaml1.yml";
        filepath2 = RESOURCESPATH + "yaml/longyaml2.yml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDPLAINLONG);
    }

    @Test
    void testDifferGenerateJSONStylishLong() throws Exception {
        format = "stylish";
        filepath1 = RESOURCESPATH + "json/longjson1.json";
        filepath2 = RESOURCESPATH + "json/longjson2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDSTYLISHLONG);
    }

    @Test
    void testDifferGenerateJSONPlainLong() throws Exception {
        format = "plain";
        filepath1 = RESOURCESPATH + "json/longjson1.json";
        filepath2 = RESOURCESPATH + "json/longjson2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDPLAINLONG);
    }

    @Test
    void testDifferGenerateJSONJsonShort() throws Exception {
        format = "json";
        filepath1 = RESOURCESPATH + "json/file1.json";
        filepath2 = RESOURCESPATH + "json/file2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDJSONSHORT);
    }

    @Test
    void testDifferGenerateJSONJsonLong() throws Exception {
        format = "json";
        filepath1 = RESOURCESPATH + "json/longjson1.json";
        filepath2 = RESOURCESPATH + "json/longjson2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDJSONLONG);
    }
    @Test
    void testDifferGenerateYAMLJsonShort() throws Exception {
        format = "json";
        filepath1 = RESOURCESPATH + "yaml/file1.yaml";
        filepath2 = RESOURCESPATH + "yaml/file2.yaml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDJSONSHORT);
    }

    @Test
    void testDifferGenerateYAMLJsonLong() throws Exception {
        format = "json";
        filepath1 = RESOURCESPATH + "yaml/longyaml1.yml";
        filepath2 = RESOURCESPATH + "yaml/longyaml2.yml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(EXPECTEDJSONLONG);
    }

    @Test
    void testDifferGenerateYAMLWithoutFormat() throws Exception {
        filepath1 = RESOURCESPATH + "yaml/longyaml1.yml";
        filepath2 = RESOURCESPATH + "yaml/longyaml2.yml";
        String dataResult = Differ.generate(filepath1, filepath2);
        assertThat(dataResult).isEqualTo(EXPECTEDSTYLISHLONG);
    }

//???????? ???? ????????????
    @Test
    void testDifferGenerateWrongFormatException() {
        format = "wrong";
        filepath1 = RESOURCESPATH + "json/file1.json";
        filepath2 = RESOURCESPATH + "json/file2.json";
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            String dataResult = Differ.generate(filepath1, filepath2, format);
        });
        assertThat(thrown.getMessage()).isEqualTo("ERROR: Don't know 'wrong' format type");
    }

    @Test
    void  testGetDataWrongTypeDataFormatException() throws IOException {
        filepath1 = RESOURCESPATH + "json/file1.json";
        String content = Differ.getString(filepath1);
        String type = "WRONG";
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            Map data = Parser.getData(content, type);
        });
        assertThat(thrown.getMessage()).isEqualTo("ERROR: Don't know 'WRONG' data format!");
    }

    @Test
    void testGetContentTypeWrongFileExtensionException() {
        filepath1 = RESOURCESPATH + "json/file1.wrong";
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            String contentType = Differ.getContentType(filepath1);
        });
        assertThat(thrown.getMessage()).isEqualTo("Don't know this extension!");
    }
}
