package com.jupiterhub.research.java8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
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

        streamOf();
        streamIterate();

        streamMap();
        streamFlatMap();


        streamReduce();
    }

    private static void streamReduce() {
        System.out.println("\n@streamReduce");
        Stream<Integer> numbers = Stream.of(1,2,3,4,5);

        Optional<Integer> intOptional = numbers.reduce((i, j) -> {
            System.out.println("total (i+j): " + (i+j));
            return i+j;
        });

        if(intOptional.isPresent()) System.out.println("Reduced numbers= "+intOptional.get());
    }

    private static void streamFlatMap() {
        System.out.println("\n@streamFlatMap");
        List<List<Integer>> collect = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4)).collect(Collectors.toList());
        System.out.println("Before FlatMap" + collect);


        List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4)) // Stream of List<Integer>
                .flatMap(List::stream)
                .map(integer -> integer)
                .collect(Collectors.toList());

        System.out.println("After FlatMap: " + together);
    }

    private static void streamMap() {
        System.out.println("\n@streamMap");
        Stream<String> names = Stream.of("John", "Williams", "Peter");
        System.out.println(names.map(String::toUpperCase)   // automatically uppercases the parameter
            .collect(Collectors.toList())
        );
    }

    private static void streamOf() {
        System.out.println("\n@streamOf");
        Map<String,Integer> keyValuePairs = Stream.of(1,2,3,4)
                .collect(Collectors.toMap(key -> "key-"+key, val -> val*2));
        System.out.println(keyValuePairs);
    }

    private static void streamIterate() {
        System.out.println("\n@streamIterate");
        Stream.iterate("value", (val) -> "function(" + val + ")")
                .limit(5)   // without this, it will be infinte
                .forEach((val) -> System.out.println(val));
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