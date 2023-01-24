package base.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileDatabase implements Database{
    private final String dbName;
    private final Path dbPath;
    private final List<EntryList> collections;

    FileDatabase(Path dbPath) {
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
                    .forEach(path -> this.collections.add(new EntryList(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public EntryList createCollection(String collName) {
        Path pathToCollection = (Path.of(dbPath.toString() + "\\" + collName + ".json"));

        File file = new File(pathToCollection.toUri());
        if (!file.exists()){
            EntryList entryList = new EntryList(pathToCollection);
            collections.add(entryList);
            return entryList;
        }

        System.out.println("file exists");
        return new EntryList(pathToCollection);
    }
}
