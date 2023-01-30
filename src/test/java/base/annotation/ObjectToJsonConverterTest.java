package base.annotation;

import base.annotation.testClient.User;
import base.database.AutoCommitDatabaseFactory;
import base.database.CollectionOfDatabase;
import base.database.Database;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectToJsonConverterTest {
    static Database db;

    @BeforeAll
    static void dbInit(){
        db = new AutoCommitDatabaseFactory().createDatabase("db");
    }
    @Test
    public void UserToDbTest() throws IOException {
        User user = new User("login", "password");

        CollectionOfDatabase collection = db.createCollection("col1").addEntry(user);
        assertTrue(collection.toString().contains("\"password\":\"password\",\"username\":\"login\""));
        assertEquals(87, Files.size(collection.getCollectionPath()));
        collection.printAll();
    }

    @Test
    public void DbToUserTest(){
        User user = (User) db.createCollection("col2").addEntry("{username:login, password:pass}")
                .getByIndex(0).asObject(User.class);
        System.out.println(user);
        assertNotNull(user.getId());
        assertEquals("login", user.getUsername());
        assertEquals("pass", user.getPassword());

    }

    @Test
    void getAllAsObjectTest(){
        CollectionOfDatabase col = db.createCollection("col3").addAllEntries(
                "{username:u1, password:p1}",
                "{username:u2, password:p2}",
                "{username:u3, password:p3}"
        );

        List<User> users = col.getAllAsObject(User.class)
                .stream()
                .map(o -> (User)o)
                .toList();

        System.out.println(users);

        assertEquals(3, users.size());
        assertEquals("p3", users.get(2).getPassword());
    }

    @AfterAll
    static void dbDelete(){
        db.deleteDatabase();
    }
}