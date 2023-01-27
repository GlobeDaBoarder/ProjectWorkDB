package base.database;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestChaining {

    @Test
    void testAutocommitDefinedThroughInterfaceChaining() throws IOException {

        DatabaseFactory factory = new AutoCommitDatabaseFactory();
        Database db = factory.createDatabase("testChainingInterface");
        CollectionOfDatabase collection = db.createCollection("col")
                .addEntry("{\"name\":\"Baeldung\",\"java\":\"true\"}")
                .addEntry("{name:Globe, surname:Ivashyn}")
                .addAllEntries(
                        "{\"name\":\"Sarah\"}",
                        "{\"name\":\"John\"}"
                )
                .addEntry("{noname:empty}")
                .addEntry("{name:Globe, surname:Ivashyn222}")
                .addEntry("{name:Globe, surname:Ivashyn, hobby:sb}")
                .updateEntry("{name:Globe}", "{name:Margo}")
                .removeEntry("{name:Baeldung}")
                .removeEntryField("{name:Margo}", "hobby");

        assertEquals(6, collection.size());
        assertEquals(430, Files.size(collection.getCollectionPath()));
        collection.printAll();
        collection.delete();
    }

    @Test
    void testAutocommitChaining() throws IOException {
    AutoCommitDatabaseFactory factoryConcrete = new AutoCommitDatabaseFactory();
    AutoCommitDatabase dbConcrete = factoryConcrete.createDatabase("testChainingConcrete");
    AutoCommitCollection collectionConcrete = dbConcrete.createCollection("colConcrete")
            .addEntry("{\"name\":\"Baeldung\",\"java\":\"true\"}")
            .addEntry("{name:Globe, surname:Ivashyn}")
            .addAllEntries(
                    "{\"name\":\"Sarah\"}",
                    "{\"name\":\"John\"}"
            )
            .addEntry("{noname:empty}")
            .addEntry("{name:Globe, surname:Ivashyn222}")
            .addEntry("{name:Globe, surname:Ivashyn, hobby:sb}")
            .updateEntry("{name:Globe}", "{name:Margo}")
            .removeEntry("{name:Baeldung}")
            .removeEntryField("{name:Margo}", "hobby");

        assertEquals(6, collectionConcrete.size());
        assertEquals(430, Files.size(collectionConcrete.getCollectionPath()));
        collectionConcrete.printAll();
        collectionConcrete.delete();
    }


    @Test
    void testManualChaining() throws IOException {
        ManualCommitDatabaseFactory factoryConcrete = new ManualCommitDatabaseFactory();
        ManualCommitDatabase dbConcrete = factoryConcrete.createDatabase("testChainingConcrete");
        ManualCommitCollection collectionConcrete = dbConcrete.createCollection("colConcrete")
                .addEntry("{\"name\":\"Baeldung\",\"java\":\"true\"}")
                .addEntry("{name:Globe, surname:Ivashyn}")
                .addAllEntries(
                        "{\"name\":\"Sarah\"}",
                        "{\"name\":\"John\"}"
                )
                .addEntry("{noname:empty}")
                .addEntry("{name:Globe, surname:Ivashyn222}")
                .addEntry("{name:Globe, surname:Ivashyn, hobby:sb}")
                .updateEntry("{name:Globe}", "{name:Margo}")
                .removeEntry("{name:Baeldung}")
                .removeEntryField("{name:Margo}", "hobby");

        assertEquals(6, collectionConcrete.size());
        assertEquals(0, Files.size(collectionConcrete.getCollectionPath()));
        collectionConcrete.commitToFile();
        assertEquals(430, Files.size(collectionConcrete.getCollectionPath()));
        collectionConcrete.printAll();
        collectionConcrete.delete();
    }



}
