package org.example;

import base.database.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntryList collection = new FileDatabaseFactory()
                .createDatabase("db")
                .createCollection("col1");
//                .add("{\"name\":\"Baeldung\",\"java\":\"true\"}")
                //.add("{name:Globe, surname:Ivashyn222}")
//                .addAll(
//                        "{\"name\":\"Sarah\"}",
//                        "{\"name\":\"John\"}"
//                )
//                .add("{noname:empty}");

        List<Entry> list = collection.getWhere("{name:Globe}");
        System.out.println(list);


    }
}