package org.example;

import base.database.EntryList;
import base.database.Database;
import base.database.DatabaseFactory;
import base.database.FileDatabaseFactory;
import base.database.Entry;

public class Main {
    public static void main(String[] args) {
        DatabaseFactory factory = new FileDatabaseFactory();
        Database db = factory.createDatabase("db");
        EntryList collection1 = db.createCollection("col1");
        EntryList collection2 = db.createCollection("col2");
        collection1.add(new Entry("{ \"name\": \"Baeldung\", \"java\": \"true\" }"));
        collection1.add(new Entry("{ \"name\": \"Globe\", \"java\": \"true\" }"));
        EntryList collection3 = db.createCollection("col1");

        System.out.println("done");
    }
}