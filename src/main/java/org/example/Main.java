package org.example;

import base.collection.EntryList;
import base.database.Database;
import base.database.DatabaseFactory;
import base.database.FileDatabaseFactory;
import base.entity.Entry;

public class Main {
    public static void main(String[] args) {
        DatabaseFactory factory = new FileDatabaseFactory();
        Database db = factory.createDatabase("db");
        EntryList collection1 = db.createCollection("col1");
        collection1.add(new Entry("{ \"name\": \"Baeldung\", \"java\": \"true\" }"));
        collection1.add(new Entry("{ \"name\": \"Globe\", \"java\": \"true\" }"));

        System.out.println("don");
    }
}