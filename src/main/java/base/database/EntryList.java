package base.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class EntryList {

    //make multi threaded
    private LinkedHashMap<UUID, Entry> collection;

    private final String collectionName;
    private final Path collPath;

    EntryList(Path pathToColl) {
        this.collectionName = pathToColl.getFileName().toString();
        this.collPath = pathToColl;
        this.collection = new LinkedHashMap<>();

        File file = new File(pathToColl.toUri());
        if (!file.exists()){
            System.out.println("creating file");
            try {
                Files.createFile(pathToColl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            useCollection(pathToColl);
        }
    }

    public void useCollection(Path pathToColl) {
        try (Stream<String> lines = Files.lines(pathToColl)) {
            lines
                    .forEach(this::readEntryIntoCollection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readEntryIntoCollection(String fullJson){
        Entry existingEntry = Entry.readExistingEntry(fullJson);
        this.collection.put(existingEntry.getUUID(), existingEntry);
    }

    public EntryList add(String jsonBody){
        Entry newEntry = Entry.createEntry(jsonBody);
        this.collection.put(newEntry.getUUID(), newEntry);
        updateFile(newEntry);
        return this;
    }

    public EntryList addAll(String... jsonBodies){
        for (String jsonBody : jsonBodies) {
            add(jsonBody);
        }
        return this;
    }

    private void updateFile(Entry entry){
        try (Writer writer = new FileWriter(collPath.toFile(), true)) {
            Gson gson = new GsonBuilder()
                    .create();
            gson.toJson(entry.getJson(), writer);
            writer.write('\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printAll(){
        getAll().forEach(System.out::println);
    }

    public void pintWithId (String searchUuid){
        System.out.println(getById(searchUuid));
    }

    public void printWithIndex(int index){
        System.out.println(get(index));
    }
    public Entry get(int index){
        return (Entry) this.collection.values().toArray()[index];
    }

    public Collection<Entry> getAll(){
        return this.collection.values();
    }

    public Entry getById(String searchUuid){
        return this.collection.get(UUID.fromString(searchUuid));
    }



    public String getCollectionName() {
        return collectionName;
    }

    public Path getCollPath() {
        return collPath;
    }
}
