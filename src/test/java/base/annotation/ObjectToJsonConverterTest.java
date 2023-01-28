package base.annotation;

import base.database.AutoCommitDatabaseFactory;
import base.database.CollectionOfDatabase;
import base.database.Database;
import base.annotation.testClient.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class ObjectToJsonConverterTest {
    User user;
    static Database db;

    @BeforeAll
    static void dbInit(){
        db = new AutoCommitDatabaseFactory().createDatabase("db");
    }
    @Test
    public void givenObjectSerializedThenTrueReturned() throws IOException {
        User user = new User("login", "password");

        CollectionOfDatabase collection = db.createCollection("col1").addEntry(user);
        assertTrue(collection.toString().contains("\"password\":\"password\",\"username\":\"login\""));
        assertEquals(87, Files.size(collection.getCollectionPath()));
        collection.printAll();
    }

    @AfterAll
    static void dbDelete(){
        db.deleteDatabase();
    }
}