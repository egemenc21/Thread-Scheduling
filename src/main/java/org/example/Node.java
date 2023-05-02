package org.example;

import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

public class Node extends Thread {
    HashSet<Node> priorityThreads = new HashSet<>();
    String name;
    Random random = new Random();

    public Node(String name) {
        this.name = name;
    }

    //Simulating the job with perform method
    public void perform() throws InterruptedException {
        System.out.println("Node" + this.name + " is being started");
        Thread.sleep(random.nextInt(2000));
        System.out.println("Node" + this.name + " is completed");
    }

    @Override
    public void run() {
        try {
            waitForPriorityThreads();
            perform();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //the thread giving needs to wait other priority threads
    private void waitForPriorityThreads() throws InterruptedException {
        if (priorityThreads.size() > 0) {
            String waitingFor = priorityThreads.stream()
                    .map(node -> node.name)
                    .collect(Collectors.joining(","));

            System.out.println("Node" + this.name + " is waiting for " + waitingFor);

            for (Node node : priorityThreads) {
                if (node.isAlive()) {
                    node.join();
                }
            }
        }
    }

    //We need to override equals and hashCode method, because we need to check if threads has the same name
    // default equals method is not capable to check on threads

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        Node other = (Node) obj;
        return this.name.equals(other.name);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
