package base.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetJsonChangeTest {
    private static final Database db = new AutoCommitDatabaseFactory().createDatabase("GetJsonDb");
    @Test
    void testGetJson() throws IOException {
        CollectionOfDatabase collection = db
                .createCollection("col1").addEntry("{test:test}");
        Entry entry = collection.getByIndex(0);
        entry.getJson().addProperty("test2", "test2");
        collection.commitToFile();
        collection.printAll();

        assertEquals(60, Files.size(collection.getCollectionPath()));
    }

    @AfterAll
    static void deleteDB(){
        db.deleteDatabase();
    }
}
