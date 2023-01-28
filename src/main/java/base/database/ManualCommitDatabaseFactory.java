package base.database;

import java.nio.file.Path;

public class ManualCommitDatabaseFactory extends DatabaseFactoryAbstract{
    public ManualCommitDatabaseFactory(Path pathToDatabase) {
        super(pathToDatabase);
    }

    public ManualCommitDatabaseFactory(String pathStringToDatabase) {
        super(pathStringToDatabase);
    }

    public ManualCommitDatabaseFactory() {
        super();
    }

    @Override
    public ManualCommitDatabase createDatabase(String databaseName) {
        return new ManualCommitDatabase(createPathOfDatabase(databaseName));
    }
}
