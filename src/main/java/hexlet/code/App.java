package hexlet.code;

import picocli.CommandLine;

public class App {
    public static void main(String[] args) {
        new CommandLine(new Differ()).execute(args);
    }
}