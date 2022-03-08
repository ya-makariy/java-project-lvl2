package hexlet.code;

//import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;


class AppTest {

    private static Map datajson1;
    private static Map datajson2;
    private static Map datayaml1;
    private static Map datayaml2;
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
        datajson1 = Parser.getData(resourcesPath + "json/file1.json");
        datajson2 = Parser.getData(resourcesPath + "json/file2.json");
        datayaml1 = Parser.getData(resourcesPath + "yaml/file1.yaml");
        datayaml2 = Parser.getData(resourcesPath + "yaml/file2.yaml");
        System.setOut(new PrintStream(outContent));
    }
    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testDifferGenerateJson() throws Exception {
        //Map map1 = Parser.getData(datajson1);
        //Map map2 = Parser.getData(datajson2);
        LinkedHashMap<String, Object> sortedmap = Differ.sorteddata(datajson1, datajson2);
        String dataResult = Differ.generate(datajson1, datajson2, sortedmap);

        assertThat(dataResult).isEqualTo(expected);
    }

    @Test
    void testDifferGenerateYaml() throws Exception {
        //Map map1 = Parser.getData(datayaml1);
        //Map map2 = Parser.getData(datayaml2);
        LinkedHashMap<String, Object> sortedmap = Differ.sorteddata(datayaml1, datayaml2);
        String dataResult = Differ.generate(datayaml1, datayaml2, sortedmap);

        assertThat(dataResult).isEqualTo(expected);
    }

    @Test
    void testAppMainJson() {
        String expectedout = expected + "\n";
        String[] appArgs = {resourcesPath + "json/file1.json", resourcesPath + "json/file2.json"};
        App.main(appArgs);
        assertThat(outContent.toString()).isEqualTo(expectedout);
    }

    //@Test
    //void testAppMainYaml() {
    //    String expectedout = expected + "\n";
    //    String[] appArgs = {resourcesPath + "file1.yaml", resourcesPath + "file2.yaml"};
    //    App.main(appArgs);
    //    assertThat(outContent.toString()).isEqualTo(expectedout);
    //}


}
