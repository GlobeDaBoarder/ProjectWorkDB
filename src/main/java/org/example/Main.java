package org.example;

import base.database.*;

import java.util.HashMap;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        DatabaseFactory factory = new FileDatabaseFactory();
        Database database = factory.createDatabase("db");
        EntryList collection1 = database.createCollection("col1");
        EntryList collection2 = database.createCollection("col2");
        collection1.add(new Entry("{ \"name\": \"Baeldung\", \"java\": \"true\" }"));
        collection1.add(new Entry("{ \"name\": \"Globe\", \"java\": \"true\" }"));
        EntryList collection3 = database.createCollection("col1");
        Database db2 = factory.createDatabase("db");

        //read functionality by id , read all, updateEntry

        System.out.println("done");

    }
}