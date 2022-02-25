package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Compares two configuration files and shows a difference.")
public class Differ implements Runnable {

    @Parameters(index = "0", description = "path to first file")
    String filepath1;

    @Parameters(index = "1", description = "path to second file")
    String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Override
    public void run() {
        System.out.println("Hello world!");
    }
}