package hexlet.code;

import picocli.CommandLine;

@CommandLine.Command(name = "app", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Print string Hello World!")
public class Differ implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello world!");
    }
}