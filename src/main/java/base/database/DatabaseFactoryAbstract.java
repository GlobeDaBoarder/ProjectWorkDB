package base.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

abstract class DatabaseFactoryAbstract implements DatabaseFactory{
    private static final Path DEFAULT_PATH = Paths.get(".").normalize().toAbsolutePath().resolve("DBs");
    protected Path pathToDatabase;

    public DatabaseFactoryAbstract(Path pathToDatabase) {
        this.pathToDatabase = pathToDatabase;
    }

    public DatabaseFactoryAbstract(String pathStringToDatabase) {
        this(Path.of(pathStringToDatabase));
    }

    public DatabaseFactoryAbstract() {
        try {
            Files.createDirectories(DEFAULT_PATH);
            this.pathToDatabase = DEFAULT_PATH;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public abstract Database createDatabase(String databaseName);

    protected Path createPathOfDatabase(String databaseName){
        return this.pathToDatabase.resolve(databaseName);
    }

}
