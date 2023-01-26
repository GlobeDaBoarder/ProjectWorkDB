package base.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AutoCommitFileDatabase implements Database{
    private final String dbName;
    private final Path dbPath;
    private final List<AutoCommitCollection> collections;

    AutoCommitFileDatabase(Path dbPath) {
        this.dbName = dbPath.getFileName().toString();
        this.collections = new ArrayList<>();
        this.dbPath = dbPath;

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

    public void useDB(Path dbPath){
        try (Stream<Path> paths = Files.walk(dbPath)) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(path -> this.collections.add(new AutoCommitCollection(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public AutoCommitCollection createCollection(String collName) {
        Path pathToCollection = (Path.of(dbPath.toString() + "\\" + collName + ".json"));

        File file = new File(pathToCollection.toUri());
        if (!file.exists()){
            AutoCommitCollection autoCommitCollection = new AutoCommitCollection(pathToCollection);
            collections.add(autoCommitCollection);
            return autoCommitCollection;
        }

        System.out.println("file exists");
        return new AutoCommitCollection(pathToCollection);
    }
}
