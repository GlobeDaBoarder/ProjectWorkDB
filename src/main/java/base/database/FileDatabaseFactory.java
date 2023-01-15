package base.database;

import java.nio.file.Path;

public class FileDatabaseFactory implements DatabaseFactory{
    @Override
    public Database createDatabase(String databaseName) {
        Path pathToDb = Path.of("D:\\CopyProject\\CopyProject\\ProjectWorkDB\\" + databaseName);
        return new FileDatabase(pathToDb);
    }
}
