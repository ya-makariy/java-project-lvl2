package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;


class AppTest {

    private static Map map1;
    private static Map map2;
    private static LinkedHashMap<String, Object> sortedmap;

    @BeforeAll
    public static void beforeAll() throws Exception {
        String resourcesPath = "src/test/resources/";
        String data1 = Differ.fileToString(resourcesPath + "file1.json");
        String data2 = Differ.fileToString(resourcesPath + "file2.json");
        map1 = Differ.getData(data1);
        map2 = Differ.getData(data2);
        sortedmap = Differ.sorteddata(map1, map2);
    }

    @Test
    void testGenerate() throws JsonProcessingException {

        String dataResult = Differ.generate(map1, map2, sortedmap);
        String expected = "{\n"
                + "  - follow:false,\n"
                + "    host:hexlet.io,\n"
                + "  - proxy:123.234.53.22,\n"
                + "  - timeout:50,\n"
                + "  + timeout:20,\n"
                + "  + verbose:true\n" + "}";

        assertThat(dataResult).isEqualTo(expected);

    }


}
