package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;


class AppTest {

    private static Map map1;
    private static Map map2;
    private static LinkedHashMap<String, Object> sortedmap;
    private static String resourcesPath = "src/test/resources/";
    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static PrintStream originalOut = System.out;
    private static String expected = "{\n"
            + "  - follow:false,\n"
            + "    host:hexlet.io,\n"
            + "  - proxy:123.234.53.22,\n"
            + "  - timeout:50,\n"
            + "  + timeout:20,\n"
            + "  + verbose:true\n" + "}";


    @BeforeAll
    public static void beforeAll() throws Exception {
        String data1 = Parser.fileToString(resourcesPath + "file1.json");
        String data2 = Parser.fileToString(resourcesPath + "file2.json");
        map1 = Parser.getData(data1);
        map2 = Parser.getData(data2);
        sortedmap = Differ.sorteddata(map1, map2);
        System.setOut(new PrintStream(outContent));
    }
    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testDifferGenerate() throws JsonProcessingException {

        String dataResult = Differ.generate(map1, map2, sortedmap);

        assertThat(dataResult).isEqualTo(expected);
    }

    @Test
    void testAppMain() {
        String expectedout = expected + "\n";
        String[] appArgs = {resourcesPath + "file1.json", resourcesPath + "file2.json"};
        App.main(appArgs);
        assertThat(outContent.toString()).isEqualTo(expectedout);
    }


}
