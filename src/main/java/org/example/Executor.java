package org.example;

import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class Executor {
    @Option(name = "-i",required = true)
    private static String path;

    HashSet<Node> threads = new HashSet<>();
    static List<String> lines = new ArrayList<>();

    //Parsing lines from "txt" file
    public static List<String> parseLines() throws IOException {
        List<String> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(Executor.path))) {
            stream.forEach(lines::add);
        } catch (Exception e) {
            System.out.println(e);
        }
        return lines;
    }

    public void createThreads() throws IOException {
        //Getting lines from 'txt' file
        lines = Executor.parseLines();

        //Iterating through the lines and setting which thread has priority or not
        for (String line : lines) {
            String[] separatedLine = line.split("->");

            if (separatedLine.length == 1) {
                Node thread = new Node(separatedLine[0]);
                thread = findOrReturn(thread);
                threads.add(thread);

            } else {
                String[] priorities = separatedLine[0].split(",");
                HashSet<Node> priorityThreads = new HashSet<>();

                for (String priorityThread : priorities) {
                    Node thread = new Node(priorityThread);
                    thread = findOrReturn(thread);
                    threads.add(thread);
                    priorityThreads.add(thread);

                }

                String nonPriorityThread = separatedLine[1];
                Node thread = new Node(nonPriorityThread);
                thread = findOrReturn(thread);
                threads.add(thread);
                thread.priorityThreads = priorityThreads;

            }
        }
    }

    //Finds the thread in HashSet or returns the giving thread
    public Node findOrReturn(Node thread) {
        if (threads.contains(thread)) {
            for (Node t : threads) {
                if (t.name.equals(thread.name)) {
                    return t;
                }
            }
        }
        return thread;
    }


    public void startThreads() {
        for (Node thread : threads) {
            thread.start();
        }
    }
}
