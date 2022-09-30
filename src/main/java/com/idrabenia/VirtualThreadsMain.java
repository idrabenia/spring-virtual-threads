package com.idrabenia;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class VirtualThreadsMain {

    public static void main(String... args) throws InterruptedException, ExecutionException {
        runThreadsExample();
        runCreateVirtualThreadExample();
        runVirtualThreadPoolExample();

        System.out.println(runStructConcurrencyExample());
    }

    private static Response runStructConcurrencyExample() throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var user = scope.fork(() -> {
                Thread.sleep(10000L);
                return "Test User";
            });

            var order = scope.fork(() -> {
                Thread.sleep(10000L);
                return 10010;
            });

            scope.join();           // Join both forks
            scope.throwIfFailed();  // ... and propagate errors

            // Here, both forks have succeeded, so compose their results
            return new Response(user.resultNow(), order.resultNow());
        }
    }

    record Response(String user, Integer order) {};

    private static void runVirtualThreadPoolExample() {
        System.out.println("Example 3:");

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(VirtualThreadsMain::printCurrentThreadName);
            executor.execute(VirtualThreadsMain::printCurrentThreadName);
            executor.execute(VirtualThreadsMain::printCurrentThreadName);
            executor.execute(VirtualThreadsMain::printCurrentThreadName);
        }
    }

    private static void runCreateVirtualThreadExample() throws InterruptedException {
        System.out.println("Example 2:");

        Thread
            .startVirtualThread(() -> System.out.println(Thread.currentThread()))
            .join();
    }

    private static void runThreadsExample() throws InterruptedException {
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
