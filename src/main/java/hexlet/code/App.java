package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import hexlet.code.Differ;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable {

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
            string1 = Differ.fileToString(filepath1);
        } catch (IOException e) {
            e.printStackTrace();
            return "error with fileToString";
        }

        String string2;

        try {
            string2 = Differ.fileToString(filepath2);
        } catch (IOException e) {
            e.printStackTrace();
            return "error with fileToString";
        }

        Map map1;
        try {
            map1 = Differ.getData(string1);
        } catch (Exception e) {
            e.printStackTrace();
            return "error with getData";
        }
        Map map2;
        try {
            map2 = Differ.getData(string2);
        } catch (Exception e) {
            e.printStackTrace();
            return "error with getData";
        }

        String data;
        try {
            data = Differ.generate(map1, map2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error with generate";
        }
        System.out.println(data);
        return "0";
    }

    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }
}