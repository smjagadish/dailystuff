package org.Appstart;

import org.work.worker;

public class App {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        worker w = new worker();
        w.doWork();
    }
}