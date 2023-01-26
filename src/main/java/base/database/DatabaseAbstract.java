package base.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

abstract class DatabaseAbstract implements Database {
    protected final String dbName;
    protected final Path dbPath;
    protected final List<CollectionOfDatabase> collections;

    public DatabaseAbstract(Path dbPath) {
        this.dbName = dbPath.getFileName().toString();
        this.dbPath = dbPath;
        this.collections = new ArrayList<>();

        File file = new File(dbPath.toUri());
        if (file.isDirectory()){
            useDB(dbPath);
        }
        else{
            try {
                Files.createDirectory(dbPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void useDB(Path dbPath) {
        try (Stream<Path> paths = Files.walk(dbPath)) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(path -> this.collections.add(new AutoCommitCollection(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public abstract CollectionOfDatabase createCollection(String collName);
}
