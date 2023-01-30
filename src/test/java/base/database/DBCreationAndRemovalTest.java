package base.database;

import org.junit.jupiter.api.*;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class DBCreationAndRemovalTest {
    Database db;

    @BeforeEach
    void init(){
        this.db = new AutoCommitDatabaseFactory().createDatabase("dbToDelete");
        assertTrue(Files.exists(db.getPath()));
        db.createCollection("col1");
        db.createCollection("col2").addEntry("{test:test}");
    }
    @Test
    void creatCollection(){
        assertEquals(2, db.getCollections().size());
    }

    @Test
    void deleteCollection(){
        db.deleteCollection("col1");
        assertEquals(1, db.getCollections().size());
    }

    @Test
    void getCollectionByNameTest(){
        CollectionOfDatabase colInit = db.createCollection("col3");
        CollectionOfDatabase colGet = db.getCollection("col3");

        assertEquals(colInit.getCollectionName(), colGet.getCollectionName());
    }

    @AfterEach
    void deleteDb(){
        db.deleteDatabase();
        assertFalse(Files.exists(db.getPath()));
    }

}
