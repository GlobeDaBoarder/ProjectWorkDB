package base.database;

import java.nio.file.Path;

public class ManualCommitDatabaseFactory implements DatabaseFactory{

    @Override
    public Database createDatabase(String databaseName) {
        Path pathToDb = Path.of("D:\\CopyProject\\CopyProject\\ProjectWorkDB\\dbs\\" + databaseName);
        return new ManualCommitDatabase(pathToDb);
    }
}
