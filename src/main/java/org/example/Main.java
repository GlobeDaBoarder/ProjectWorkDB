package org.example;

import base.database.Entry;
import base.database.EntryList;
import base.database.FileDatabaseFactory;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //               CRUD
        //CREATE
        EntryList collection = new FileDatabaseFactory()
                .createDatabase("db")
                .createCollection("col1");
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



//        collection.printAll()
//                .update("{name:Globe, surname:Ivashyn}", "{surname:Hakkem, age:20}")
//                .printAll();
//
//        collection.removeEntryField("{name:Globe, surname:Hakkem}", "surname", "hobby", "age");
//
//        collection.remove("{name:Globe}");
//
//        collection.clear();

    }
}