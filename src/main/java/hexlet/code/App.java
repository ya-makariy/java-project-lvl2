package hexlet.code;

import picocli.CommandLine;
//import picocli.CommandLine.Command;
//import picocli.CommandLine.Option;
//import picocli.CommandLine.Parameters;

public class App {
    public static void main(String[] args) {

        CommandLine.run(new Differ(), args);

    }
}