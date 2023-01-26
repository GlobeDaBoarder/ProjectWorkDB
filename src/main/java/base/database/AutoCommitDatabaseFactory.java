package base.database;

import java.nio.file.Path;

public class AutoCommitDatabaseFactory implements DatabaseFactory{
    @Override
    public AutoCommitDatabase createDatabase(String databaseName) {
        Path pathToDb = Path.of("D:\\CopyProject\\CopyProject\\ProjectWorkDB\\dbs\\" + databaseName);
        return new AutoCommitDatabase(pathToDb);
    }
}
