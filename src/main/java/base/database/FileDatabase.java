package base.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileDatabase implements Database{
    private final String dbName;
    private final Path dbPath;
    private final List<EntryList> collections;

    FileDatabase(Path dbPath) {
        this.dbName = dbPath.getFileName().toString();
        this.collections = new ArrayList<>();
        this.dbPath = dbPath;

        try {
            Files.createDirectory(dbPath);
        } catch (FileAlreadyExistsException e) {
            System.out.println("such directory already exists. Name: " + dbName);
            //perform db read into collections
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public EntryList createCollection(String collName) {
        Path pathToCollection = (Path.of(dbPath.toString() + "\\" + collName + ".json"));

        File file = new File(pathToCollection.toUri());
        if (!file.exists()){
            System.out.println("file doesn't exist");
            EntryList entryList = new EntryList(pathToCollection);
            collections.add(entryList);
            return entryList;
        }

        System.out.println("file exists");
        return new EntryList(pathToCollection);
    }
}
