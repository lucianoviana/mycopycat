package com;

import java.util.ArrayList;
import java.util.List;

public class Covar {

    static class Animal{

    }

    static class Dog extends Animal {}
    static class Cat extends Animal {}
    static class SRD extends Dog {}

    public static void main(String[] a){
        List<Dog> dogs = new ArrayList<>();
        List<Animal> animals = new ArrayList<>();
        animals.add(new Cat());

        e(animals);
        f(animals);

        g(dogs);
        g(animals);

    }

    static void e(List<Animal> list) {
    }

    static void f (List<? super Dog> list){
        list.add(new Dog());
        list.add(new SRD());
        System.out.println(list);
    }


    static void g(List<? extends Animal> list) {
        Dog d = (Dog) list.get(0);
    }

}
