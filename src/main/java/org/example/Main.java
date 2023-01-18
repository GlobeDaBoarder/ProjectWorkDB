package org.example;

import base.database.*;

public class Main {
    public static void main(String[] args) {
        EntryList collection = new FileDatabaseFactory()
                .createDatabase("db")
                .createCollection("col1")
                .add("{\"name\":\"Baeldung\",\"java\":\"true\"}")
                .add("{\"name\":\"Globe\",\"java\":\"true\"}")
                .addAll(
                        "{\"name\":\"Sarah\"}",
                        "{\"name\":\"John\"}"
                );


        System.out.println("done");

    }
}