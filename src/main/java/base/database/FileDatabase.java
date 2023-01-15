package base.database;

import base.collection.EntryList;
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
        if (file.exists() && !file.isDirectory()){
            System.out.println("Such collection already exists");

            //do retrieval of data from file, if exisits

//            EntryList entryList = new EntryList();//temp
//            return entryList;
            throw new IllegalArgumentException("file exisits");

            //return new EntryList();
        }

        EntryList entryList = new EntryList(pathToCollection);

        collections.add(entryList);

        return entryList;
    }
}
