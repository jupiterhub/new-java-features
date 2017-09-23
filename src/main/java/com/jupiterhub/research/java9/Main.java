package com.jupiterhub.research.java9;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    // Factory methods for collections. wooo!
    List<String> letter = List.of("a", "b", "c", "d");
    Set<Integer> ids = Set.of(1, 2, 3, 4);
    Map<Integer, String> letterAndIds = Map.of(1,"a", 2, "b",3, "c");

    public static void main(String... args) {
        Main main = new Main();
        System.out.println("Letters~~~");
        main.letter.stream().forEach(System.out::println);

        System.out.println("Ids~~~");
        main.ids.stream().forEach(System.out::println);

        System.out.println("Letters and ids~~~");
        main.letterAndIds.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}