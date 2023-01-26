package org.example;

import base.database.*;

public class Main {
    public static void main(String[] args) {

        //               CRUD
        //CREATE
//        CollectionOfDatabase collection = new AutoCommitDatabaseFactory()
//                .createDatabase("db")
//                .createCollection("col1");
//                .add("{\"name\":\"Baeldung\",\"java\":\"true\"}")
//                .add("{name:Globe, surname:Ivashyn}")
//                .addAll(
//                        "{\"name\":\"Sarah\"}",
//                        "{\"name\":\"John\"}"
//                )
//                .add("{noname:empty}")
//                .add("{name:Globe, surname:Ivashyn222}")
//                .add("{name:Globe, surname:Ivashyn, hobby:sb}");

        //READ

//        System.out.println(collection.getById("d8aee08d-939d-4373-9ef7-0d62a98e2bba"));
//        System.out.println(collection.getByIndex(6));
//        System.out.println(collection.getWhere("{name:Globe}"));
//        System.out.println(collection.getWhereKeyExists("name", "surname"));


//
//        collection.printAll()
//                .update("{name:Globe, surname:Ivashyn}", "{surname:Hakkem, age:20}")
//                .printAll();
//
//        collection.removeEntryField("{name:Globe, surname:Hakkem}", "surname", "hobby", "age");
//
//        collection.remove("{name:Globe}");
//
//        collection.clear();

        ManualCommitDatabaseFactory factory = new ManualCommitDatabaseFactory();
        ManualCommitDatabase db = factory.createDatabase("mainDb");
        ManualCommitCollection collection = db.createCollection("col1");
        collection.add("{name:Globe}");
        Entry entry = collection.getByIndex(0);


    }
}