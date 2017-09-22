package com.jupiterhub.research.java8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

public class Main {
    private static List<Integer> numbers;

    static {
        numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) numbers.add(i);
    }

    public static void main(String... args) throws IOException {
        doForEach();
        functionalInterface();
        streamFilterEvenNumbersOnly();
        printUsingNewDateMethods();
        readFileAsAStream();

    }

    private static void readFileAsAStream() throws IOException {
        System.out.println("\n@readFileAsAStream");
        Path path = Paths.get("src/main/resources/flashlight-lyrics.txt");
        // this is efficient because it only stores per line in the memory
        Stream<String> stream = Files.lines(path);
        stream.filter(line -> line.contains("I"))
                .forEach(System.out::println);
    }

    private static void printUsingNewDateMethods() {
        System.out.println("\n@printUsingNewDateMethods");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowInLondon = LocalDateTime.now(ZoneId.of("Europe/London"));


        System.out.println("Local time: " + now);
        System.out.println("London time: " + nowInLondon);
        System.out.println("Last day of the month: " + nowInLondon.with(TemporalAdjusters.lastDayOfMonth()));
    }

    private static void streamFilterEvenNumbersOnly() {
        System.out.println("\n@streamFilterEvenNumbersOnly");
        numbers.stream()
                .filter(number -> number % 2 == 0)
                .forEach(number -> System.out.print(" " + number));
    }

    private static void functionalInterface() {
        System.out.println("\n@functionalInterface");
        GreeterFunctionalInterface functional = words -> System.out.println("Hello" + words);
        functional.saySomething(" world");

        GreeterFunctionalInterface.sayHello();
    }

    private static void doForEach() {
        System.out.println("\n@doForEach");
        // Consumer is a functional interface
        // functional interfaces only have 1 method
        // we receive the parameter here and manipulate it
        numbers.forEach((number) -> System.out.print(" " + number));
    }
}