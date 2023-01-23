package org.example;

import base.database.Entry;
import base.database.EntryList;
import base.database.FileDatabaseFactory;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntryList collection = new FileDatabaseFactory()
                .createDatabase("db")
                .createCollection("col1")
                .add("{\"name\":\"Baeldung\",\"java\":\"true\"}")
                .add("{name:Globe, surname:Ivashyn}")
                .addAll(
                        "{\"name\":\"Sarah\"}",
                        "{\"name\":\"John\"}"
                )
                .add("{noname:empty}")
                .add("{name:Globe, surname:Ivashyn222}")
                .add("{name:Globe, surname:Ivashyn, hobby:sb}");

//        List<Entry> list = collection.getWhere("{name:Globe, surname:Ivashyn}");
//        System.out.println(list);
//
//        List<Entry> list1 = collection.getWhereKeyExists("name", "surname", "hobby");
//        System.out.println(list1);
//
        collection.printAll();
        collection.update("{name:Globe, surname:Ivashyn}", "{surname:Hakkem, age:20}");
        collection.printAll();

        collection.removeEntryField("{name:Globe, surname:Hakkem}", "surname", "hobby", "age");

//        collection.delete

    }
}