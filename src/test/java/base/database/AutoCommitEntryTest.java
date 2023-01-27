package base.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AutoCommitEntryTest {
    AutoCommitCollection collection;
    Entry entry;
    AutoCommitDatabase db;

    @BeforeEach
    void init(){
        this.db = new AutoCommitDatabaseFactory()
                .createDatabase("testEntryAutomatic");
        this.collection = db
                .createCollection("testCollection")
                .useCollection(Path.of("src/test/resources/sampleCollection.json"));

        this.entry = collection.getByIndex(0);
    }

    @AfterEach
    void afterEach(){
        this.db.deleteDatabase();
    }

    @Test
    void testCreate() throws IOException {
        assertEquals(521, Files.size(this.collection.getCollectionPath()));
        assertEquals(7, this.collection.size());
        System.out.println(this.collection);
    }
    @Test
    void editEntry() throws IOException {
        this.entry.editEntry("{name:A}");
        assertEquals(514, Files.size(this.collection.getCollectionPath()));
        assertEquals(7, this.collection.size());
        assertEquals("{\"id\":\"4c656293-2b1c-44d4-8dcf-0e5118bf146f\",\"name\":\"A\",\"java\":\"true\"}",
                this.entry.getJson().toString());
    }

    @Test
    void removeField() throws IOException {
        this.entry.removeField("java");
        assertEquals(507, Files.size(this.collection.getCollectionPath()));
        assertEquals(7, this.collection.size());
        assertEquals("{\"id\":\"4c656293-2b1c-44d4-8dcf-0e5118bf146f\",\"name\":\"Baeldung\"}",
                this.entry.getJson().toString());
    }
}