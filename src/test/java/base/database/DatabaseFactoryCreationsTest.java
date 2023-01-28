package base.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseFactoryCreationsTest {

    @Test
    void defaultPathTest(){
        Database db = new AutoCommitDatabaseFactory().createDatabase("db1");
        assertEquals("D:\\CopyProject\\CopyProject\\ProjectWorkDB\\DBs\\db1", db.getPath().toString());
        db.deleteDatabase();
    }

    @Test
    void differentPathTest(){
        Database db = new AutoCommitDatabaseFactory("D:\\DBTestFolder")
                .createDatabase("db2");
        assertEquals("D:\\DBTestFolder\\db2", db.getPath().toString());
        db.deleteDatabase();
    }

    @Test
    void differentPathManual(){
        Database db = new ManualCommitDatabaseFactory("D:\\DBTestFolder")
                .createDatabase("db3");
        assertEquals("D:\\DBTestFolder\\db3", db.getPath().toString());
        db.deleteDatabase();
    }
}
