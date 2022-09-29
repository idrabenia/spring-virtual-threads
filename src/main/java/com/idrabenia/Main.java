package com.idrabenia;

import java.util.concurrent.Executors;

public class Main {

    public static void main(String... args) throws InterruptedException {
        runExample1();
        runExample2();
        runExample3();
    }

    private static void runExample3() {
        System.out.println("Example 3:");

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(Main::printCurrentThreadName);
            executor.execute(Main::printCurrentThreadName);
            executor.execute(Main::printCurrentThreadName);
            executor.execute(Main::printCurrentThreadName);
        }
    }

    private static void runExample2() throws InterruptedException {
        System.out.println("Example 2:");

        Thread
            .startVirtualThread(() -> System.out.println(Thread.currentThread()))
            .join();
    }

    private static void runExample1() throws InterruptedException {
        System.out.println("Example 1:");

        var thread = new Thread("My Thread") {
            public void run() {
                System.out.println("run by: " + getName());
            }
        };

        thread.start();

        System.out.println(thread.getName());

        thread.join();
    }

    private static void printCurrentThreadName() {
        System.out.println(Thread.currentThread());
    }
}
