package org.example;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Executor executor = new Executor();
        CmdLineParser cmdLineParser = new CmdLineParser(executor);

        try {
            cmdLineParser.parseArgument(args);
            executor.createThreads();
            executor.startThreads();
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}