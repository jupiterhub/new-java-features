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

/**
 * Good commonon pitfalls on streams:
 * https://blog.jooq.org/2014/06/13/java-8-friday-10-subtle-mistakes-when-using-the-streams-api/
 * <p>
 * Cheat Sheet
 * http://files.zeroturnaround.com/pdf/zt_java8_streams_cheat_sheet.pdf
 */
public class Main {
    private static List<Integer> numbers;

    static {
        numbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) numbers.add(i);
    }

    public static void main(String... args) throws IOException {
        lambdaVsNormalAnonymous();

        doForEach();
        functionalInterface();
        printUsingNewDateMethods();
        readFileAsAStream();

        streamFilterEvenNumbersOnly();
        streamFilterEvenNumbersOnlyParallel();

        streamOf();
        streamIterate();
        streamMap();
        streamFlatMap();
        streamReduce();
        streamSum();
        streamMatch();
        streamFindFirst();
        streamGenerate();
    }

    private static void lambdaVsNormalAnonymous() {
        // for IntelliJ users use shift+ctrl+space to autocomplete lambda
        Runnable runLambda = () -> System.out.println("yo");

        Runnable run = new Runnable() {

            @Override
            public void run() {
                System.out.println("YO");
            }
        };
        runLambda.run();
        run.run();
    }

    private static void streamGenerate() {
        System.out.println("\n@streamGenerate");

        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);
    }

    private static void streamFindFirst() {
        System.out.println("\n@streamFindFirst");
        String[] data = {"John", "Paul", "Mark"};
        System.out.println("data: " + Arrays.asList(data));
        Stream<String> names = Stream.of(data);
        Optional<String> nameThatHasLetterA = names.filter(i -> i.contains("a")).findFirst();

        if (nameThatHasLetterA.isPresent()) {
            System.out.println("First name with letter 'a' is " + nameThatHasLetterA.get());
        }
    }

    private static void streamMatch() {
        System.out.println("\n@streamMatch");
        Stream<Integer> numbers3 = Stream.of(1, 2, 3, 4, 5);
        System.out.println("Stream contains 4? " + numbers3.anyMatch(i -> i == 4));
//Stream contains 4? true

        Stream<Integer> numbers4 = Stream.of(1, 2, 3, 4, 5);
        System.out.println("Stream contains all elements less than 10? " + numbers4.allMatch(i -> i < 10));
//Stream contains all elements less than 10? true

        Stream<Integer> numbers5 = Stream.of(1, 2, 3, 4, 5);
        System.out.println("Stream doesn't contain 10? " + numbers5.noneMatch(i -> i == 10));
//Stream doesn't contain 10? true
    }

    private static void streamSum() {
        System.out.println("\n@streamSum");
        Stream<Integer> numbers1 = Stream.of(1, 2, 3, 4, 5);
        System.out.println("Count: " + numbers1.count());
    }

    private static void streamReduce() {
        System.out.println("\n@streamReduce");
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);

        Optional<Integer> intOptional = numbers.reduce((i, j) -> {
            System.out.println("total (i+j): " + (i + j));
            return i + j;
        });

        if (intOptional.isPresent()) System.out.println("Reduced numbers= " + intOptional.get());
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
        Map<String, Integer> keyValuePairs = Stream.of(1, 2, 3, 4)
                .collect(Collectors.toMap(key -> "key-" + key, val -> val * 2));
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
        stream.filter(line -> line.contains("flashlight"))
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

    private static void streamFilterEvenNumbersOnlyParallel() {
        System.out.println("\n@streamFilterEvenNumbersOnlyParallel");
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