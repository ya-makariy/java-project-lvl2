package hexlet.code;


import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {
    private static String filepath1;
    private static String filepath2;
    private static String format;
    private static final String resourcesPath = "src/test/resources/";
    private static final String expectedStylishShort = """
            {
              - follow: false
                host: hexlet.io
              - proxy: 123.234.53.22
              - timeout: 50
              + timeout: 20
              + verbose: true
            }
            """;
    private static final String expectedPlainShort = """
            Property 'follow' was removed
            Property 'proxy' was removed
            Property 'timeout' was updated. From 50 to 20
            Property 'verbose' was added with value: true
            """;
    private static final String expectedStylishLong = """
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
    private static final String expectedPlainLong = """
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
            Property 'setting3' was updated. From true to 'none'
            """;

    @Test
    void testDifferGenerateJsonStylishShort() throws Exception {
        format = "stylish";
        filepath1 = resourcesPath + "json/file1.json";
        filepath2 = resourcesPath + "json/file2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(expectedStylishShort);
    }

    @Test
    void testDifferGenerateJsonPlainShort() throws Exception {
        format = "plain";
        filepath1 = resourcesPath + "json/file1.json";
        filepath2 = resourcesPath + "json/file2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(expectedPlainShort);
    }

    @Test
    void testDifferGenerateYamlStylishShort() throws Exception {
        format = "stylish";
        filepath1 = resourcesPath + "yaml/file1.yaml";
        filepath2 = resourcesPath + "yaml/file2.yaml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(expectedStylishShort);
    }

    @Test
    void testDifferGenerateYamlPlainShort() throws Exception {
        format = "plain";
        filepath1 = resourcesPath + "yaml/file1.yaml";
        filepath2 = resourcesPath + "yaml/file2.yaml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(expectedPlainShort);
    }

    @Test
    void testDifferGenerateYamlStylishLong() throws Exception {
        format = "stylish";
        filepath1 = resourcesPath + "yaml/longyaml1.yml";
        filepath2 = resourcesPath + "yaml/longyaml2.yml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(expectedStylishLong);
    }

    @Test
    void testDifferGenerateYamlPlainLong() throws Exception {
        format = "plain";
        filepath1 = resourcesPath + "yaml/longyaml1.yml";
        filepath2 = resourcesPath + "yaml/longyaml2.yml";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(expectedPlainLong);
    }

    @Test
    void testDifferGenerateJsonStylishLong() throws Exception {
        format = "stylish";
        filepath1 = resourcesPath + "json/longjson1.json";
        filepath2 = resourcesPath + "json/longjson2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(expectedStylishLong);
    }

    @Test
    void testDifferGenerateJsonPlainLong() throws Exception {
        format = "plain";
        filepath1 = resourcesPath + "json/longjson1.json";
        filepath2 = resourcesPath + "json/longjson2.json";
        String dataResult = Differ.generate(filepath1, filepath2, format);
        assertThat(dataResult).isEqualTo(expectedPlainLong);
    }

//Тест на ошибку
    //@Test
    //void testDifferentWrongFormatException() throws Exception {
    //    format = "wrong";
    //    filepath1 = resourcesPath + "json/file1.json";
    //    filepath2 = resourcesPath + "json/file2.json";
    //    assertThrows(Differ.generate(filepath1,filepath2,format), );
    //}
}
