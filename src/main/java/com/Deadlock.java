package com;

import java.util.Random;

public class Deadlock {


    public static void main(String[] arg) {

        Integer a = 1;
        Integer b = 2;

        new Thread(() -> {
            while (true) {
                synchronized (a) {
                    System.out.println(Thread.currentThread() + " locking a " + System.currentTimeMillis());
                    synchronized (b) {
                        System.out.println(Thread.currentThread() + " locking b " + System.currentTimeMillis());
                    }
                }
            }
        }).start();


        new Thread(() -> {
            while (true) {
                synchronized (b) {
                    System.out.println(Thread.currentThread() + " locking b " + System.currentTimeMillis());
                    synchronized (a) {
                        System.out.println(Thread.currentThread() + " locking a " + System.currentTimeMillis());
                    }
                }
            }
        }).start();

    }
}
