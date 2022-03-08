package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

//import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable {

    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Override
    public String call() {
        //String string1 = null;
//
        //try {
        //    string1 = Parser.fileToString(filepath1);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //    //return "error with fileToString";
        //}
//
        //String string2 = null;
//
        //try {
        //    string2 = Parser.fileToString(filepath2);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //    //return "error with fileToString";
        //}

        Map map1 = null;
        try {
            map1 = Parser.getData(filepath1);
        } catch (Exception e) {
            e.printStackTrace();
            //return "error with getData";
        }
        Map map2 = null;
        try {
            map2 = Parser.getData(filepath2);
        } catch (Exception e) {
            e.printStackTrace();
            //return "error with getData";
        }

        LinkedHashMap<String, Object> sortedmap;
        sortedmap = Differ.sorteddata(map1, map2);

        String data = null;
        try {
            data = Differ.generate(map1, map2, sortedmap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //return "error with generate";
        }
        System.out.println(data);
        return "0";
    }

    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }
}
