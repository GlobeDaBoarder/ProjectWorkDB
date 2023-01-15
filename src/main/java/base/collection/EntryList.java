package base.collection;

import base.entity.Entry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class EntryList extends ArrayList<Entry> {

    private final String collectionName;
    private final Path collPath;

//    //this is a test constructor. Should be removed later
//    public EntryList() {
//    }

    public EntryList(Path pathToColl) {
        collectionName = pathToColl.getFileName().toString();
        this.collPath = pathToColl;

        try {
            Files.createFile(pathToColl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Entry getById(String uuid){
        for (Entry currentEntry : this) {
            if (uuid.equals(currentEntry.getObjId())) {
                return currentEntry;
            }
        }

        System.err.println("No entity with UUID = " + uuid );
        return new Entry();
    }

    @Override
    public boolean add(Entry entry) {
        boolean flag = super.add(entry);
        if(flag)
            updateFile(entry);
        return flag;
    }

    @Override
    public void add(int index, Entry entry) {
        super.add(index, entry);
        updateFile(entry);
    }

    private void updateFile(Entry entry){
//        Gson gson = new Gson();
//        try {
//            gson.toJson(entry, new FileWriter(collPath.toString()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            Files.write(collPath, entry.getJson().toString().getBytes(), StandardOpenOption.APPEND);
//        }catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        try (Writer writer = new FileWriter(collPath.toFile(), true)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(entry, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCollectionName() {
        return collectionName;
    }

    public Path getCollPath() {
        return collPath;
    }
}
