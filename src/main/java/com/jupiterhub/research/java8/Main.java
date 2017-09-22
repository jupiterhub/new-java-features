package com.jupiterhub.research.java8;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String... args) {
        doForEach();
        functionalInterface();
    }

    private static void functionalInterface() {
        GreeterFunctionalInterface functional = (words) -> System.out.println("Hello" + words);
        functional.saySomething(" world");

        GreeterFunctionalInterface.sayHello();
    }

    private static void doForEach() {
        List<Integer> numbers = new ArrayList();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);

        // Consumer is a functional interface
        // functional interfaces only have 1 method
        // we receive the parameter here and manipulate it
        numbers.forEach((number) -> System.out.println(number));
    }
}