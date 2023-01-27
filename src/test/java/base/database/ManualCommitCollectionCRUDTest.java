package base.database;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ManualCommitCollectionCRUDTest {

    ManualCommitCollection collection;
     ManualCommitDatabase db;

    @BeforeEach
    void init(){
        db = new ManualCommitDatabaseFactory()
                .createDatabase("testCollectionManual");
        this.collection = db
                .createCollection("testCollection")
                .useCollection(Path.of("src/test/resources/sampleCollection.json"));
    }

    @AfterEach
    void afterEach(){
        this.db.deleteDatabase();
    }

    @Test
    void testCreate() throws IOException {
        assertEquals(0, Files.size(this.collection.getCollectionPath()));
        assertEquals(7, this.collection.size());
        this.collection.commitToFile();
        assertEquals(521, Files.size(this.collection.getCollectionPath()));
        assertEquals(7, this.collection.size());
        System.out.println(this.collection);
    }

    @Test
    void testAdd() throws IOException {
        this.collection
                .addEntry("{\"name\":\"Baeldung\",\"java\":\"true\"}")
                .addEntry("{name:Globe, surname:Ivashyn}")
                .addAllEntries(
                        "{\"name\":\"Sarah\"}",
                        "{\"name\":\"John\"}"
                )
                .addEntry("{noname:empty}")
                .addEntry("{name:Globe, surname:Ivashyn222}")
                .addEntry("{name:Globe, surname:Ivashyn, hobby:sb}");
        assertEquals(0, Files.size(this.collection.getCollectionPath()));
        assertEquals(14, this.collection.size());
        this.collection.commitToFile();
        assertEquals(1042, Files.size(this.collection.getCollectionPath()));
        assertEquals(14, this.collection.size());

        System.out.println(this.collection);
    }

    @Test
    void testReadGetById(){

        System.out.println(collection.getById("86a169a8-82b4-4c4a-bc7b-1f0a71b1a195"));
        assertEquals("""
                Entry{fullJson={"id":"86a169a8-82b4-4c4a-bc7b-1f0a71b1a195","name":"Globe","surname":"Ivashyn","hobby":"sb"}}""",
                collection.getById("86a169a8-82b4-4c4a-bc7b-1f0a71b1a195").toString()
        );
    }

    @Test
    void testReadGetByIndex(){
        System.out.println(collection.getByIndex(6));
        assertEquals("""
                Entry{fullJson={"id":"86a169a8-82b4-4c4a-bc7b-1f0a71b1a195","name":"Globe","surname":"Ivashyn","hobby":"sb"}}""",
                collection.getByIndex(6).toString()
        );
    }


    @Test
    void testReadGetWhere(){
        System.out.println(collection.getWhere("{name:Globe, surname:Ivashyn}"));
        assertEquals("""
                [Entry{fullJson={"id":"e815a45e-e82f-447d-8e4a-60f69dcdbf37","name":"Globe","surname":"Ivashyn"}}, Entry{fullJson={"id":"a43c502a-1eba-4bc8-9e29-c233e16973e8","name":"Globe","surname":"Ivashyn222"}}, Entry{fullJson={"id":"86a169a8-82b4-4c4a-bc7b-1f0a71b1a195","name":"Globe","surname":"Ivashyn","hobby":"sb"}}]""",
                collection.getWhere("{name:Globe}").toString()
        );
    }

    @Test
    void testReadGetWhereKeyExists(){
        System.out.println(collection.getWhereKeyExists("hobby", "surname"));
        assertEquals("""
                [Entry{fullJson={"id":"86a169a8-82b4-4c4a-bc7b-1f0a71b1a195","name":"Globe","surname":"Ivashyn","hobby":"sb"}}]""",
                collection.getWhereKeyExists("hobby", "surname").toString()
        );
    }

    @Test
    void testUpdate() throws IOException {
        this.collection.updateEntry("{name:Globe, surname:Ivashyn}",
                "{surname:Hakkem, age:20}");
        assertEquals(0, Files.size(this.collection.getCollectionPath()));
        assertEquals(7, this.collection.size());
        this.collection.commitToFile();
        assertEquals(537, Files.size(this.collection.getCollectionPath()));
        assertEquals(7, this.collection.size());

        System.out.println(this.collection);
    }

    @Test
    void testRemoveEntryField() throws IOException {
        this.collection.removeEntryField("{name:Globe, surname:Ivashyn}",
                "surname", "hobby", "age");
        assertEquals(0, Files.size(this.collection.getCollectionPath()));
        assertEquals(7, this.collection.size());
        this.collection.commitToFile();
        assertEquals(468, Files.size(this.collection.getCollectionPath()));
        assertEquals(7, this.collection.size());


        System.out.println(this.collection);
    }

    @Test
    void testRemoveEntry() throws IOException {
        this.collection.removeEntry("{name:Globe}");
        assertEquals(0, Files.size(this.collection.getCollectionPath()));
        assertEquals(4, this.collection.size());
        this.collection.commitToFile();
        assertEquals(262, Files.size(this.collection.getCollectionPath()));
        assertEquals(4, this.collection.size());


        System.out.println(this.collection);
    }

    @Test
    void testClear() throws IOException {
        this.collection.clear();
        assertEquals(0, Files.size(this.collection.getCollectionPath()));
        assertEquals(0, this.collection.size());
    }

//    @Test
//    void testDelete(){
//        this.collection.delete();
//        assertThrows(NullPointerException.class, () -> this.collection.size());
//        assertThrows(NullPointerException.class, () -> this.collection.addEntry("{test:test}"));
//    }

}