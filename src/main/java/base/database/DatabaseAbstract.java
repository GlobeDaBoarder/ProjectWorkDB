package base.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

abstract class DatabaseAbstract implements Database {
    protected final String nameOfDatabase;
    protected final Path pathToDatabase;
    protected final List<CollectionOfDatabase> collections;

    public DatabaseAbstract(Path pathToDatabase) {
        this.nameOfDatabase = pathToDatabase.getFileName().toString();
        this.pathToDatabase = pathToDatabase;
        this.collections = new ArrayList<>();

        File file = new File(pathToDatabase.toUri());
        if (file.isDirectory()){
            useDB(pathToDatabase);
        }
        else{
            try {
                Files.createDirectory(pathToDatabase);
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

    @Override
    public void deleteDatabase() {
        this.collections.forEach(collection ->{
            try {
                Files.delete(collection.getCollectionPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        try {
            Files.deleteIfExists(this.pathToDatabase);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCollection(String collectionName) {
        for (CollectionOfDatabase collection : this.collections) {
            if(collection.getCollectionName().equals(collectionName.concat(".json"))) {
                try {
                    this.collections.remove(collection);
                    Files.delete(collection.getCollectionPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public Path getPath() {
        return this.pathToDatabase;
    }

    @Override
    public List<CollectionOfDatabase> getCollections() {
        return this.collections;
    }
}
