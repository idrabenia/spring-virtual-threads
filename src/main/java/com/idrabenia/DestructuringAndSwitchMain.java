package com.idrabenia;

import java.time.Instant;

public class DestructuringAndSwitchMain {

    public static void main(String... args) {
        destructuringExample();

        switchExample(new First(1));
        switchExample(null);
        switchExample(new Third(Instant.now()));

        classicalSwitchExample("test");
        classicalSwitchExample(null);
        classicalSwitchExample(new Second("Some Name"));
    }

    record Pair<T>(T first, T second) {}

    private static void destructuringExample() {
        var a = new Pair<>(1, 2);

        if (a instanceof Pair<Integer>(var first, var second)) {
            System.out.println("Pair " + first + " " + second);
        }
    }

    record First(Integer num) {}
    record Second(String name) {}
    record Third(Instant time) {}

    private static void switchExample(Object numerable) {
        switch (numerable) {
            case null -> System.out.println("null");
            case First val when val.num() >= 0 -> System.out.println("First: " + val + " num >= 0");
            case First(var val) when val < 0 -> System.out.println("First: " + val + " num is negative");
            case Second(var val) -> System.out.println("Second: " + val);
            case Third(var val) -> System.out.println("Third: " + val);
            default -> System.out.println("Default");
        }
    }

    private static void classicalSwitchExample(Object item) {
        switch (item) {
            case null, String s:
                System.out.println("String " + s);
                break;

            case Second(String name):
                System.out.println("Second: " + name);
                break;

            case default:
                System.out.println("Default");
        }
    }

}
