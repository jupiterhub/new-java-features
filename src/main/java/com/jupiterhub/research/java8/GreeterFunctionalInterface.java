package com.jupiterhub.research.java8;

@FunctionalInterface
public interface GreeterFunctionalInterface {

    void saySomething(String word);

    default void implementingClassDoesNotNeedToOverrideTHis() {
        System.out.println("Implementing class does not have to implement me");
    }

    static void sayHello() {
        System.out.println("Hello from static call!");
    }
}
