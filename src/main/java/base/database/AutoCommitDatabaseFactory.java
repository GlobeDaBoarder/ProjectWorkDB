package base.database;

import java.nio.file.Path;

public class AutoCommitDatabaseFactory extends DatabaseFactoryAbstract{

    public AutoCommitDatabaseFactory(Path pathToDatabase) {
        super(pathToDatabase);
    }

    public AutoCommitDatabaseFactory(String pathStringToDatabase) {
        super(pathStringToDatabase);
    }

    public AutoCommitDatabaseFactory() {
        super();
    }

    @Override
    public AutoCommitDatabase createDatabase(String databaseName) {
        return new AutoCommitDatabase(createPathOfDatabase(databaseName));
    }
}
