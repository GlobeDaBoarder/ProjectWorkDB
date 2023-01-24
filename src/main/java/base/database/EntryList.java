package base.database;

import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntryList {

    //make multi threaded
    private Map<UUID, Entry> collection;

    private String collectionName;
    private Path collectionPath;

    EntryList(Path pathToColl) {
        this.collectionName = pathToColl.getFileName().toString();
        this.collectionPath = pathToColl;
        this.collection = new LinkedHashMap<>();

        File file = new File(pathToColl.toUri());
        if (!file.exists()){
            try {
                Files.createFile(pathToColl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            loadCollection(pathToColl);
        }
    }

    private void loadCollection(Path collectionPath){
        try (Stream<String> lines = Files.lines(collectionPath)) {
            lines
                    .forEach(this::readEntryIntoCollection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EntryList useCollection(Path collectionPath) {
        loadCollection(collectionPath);
        commitToFile();
        return this;
    }

    private void readEntryIntoCollection(String fullJson){
        Entry existingEntry = Entry.readExistingEntry(fullJson);
        this.collection.put(existingEntry.getUUID(), existingEntry);
    }

    public EntryList add(String jsonBody){
        Entry newEntry = Entry.createEntry(jsonBody);
        this.collection.put(newEntry.getUUID(), newEntry);
        addToFile(newEntry);
        return this;
    }

    public EntryList addAll(String... jsonBodies){
        for (String jsonBody : jsonBodies) {
            add(jsonBody);
        }
        return this;
    }

    private void addToFile(Entry entry){
        try (Writer writer = new FileWriter(collectionPath.toFile(), true)) {
            Gson gson = new GsonBuilder()
                    //.setPrettyPrinting()
                    .create();
            gson.toJson(entry.getJson(), writer);
            writer.write('\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void commitToFile(){
        try (Writer writer = new FileWriter(collectionPath.toFile(), false)) {
            this.collection.values().forEach(this::addToFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EntryList printAll(){
        getAll().forEach(System.out::println);
        System.out.println('\n');
        return this;
    }

    public void pintWithId (String searchUuid){
        System.out.println(getById(searchUuid));
    }

    public void printWithIndex(int index){
        System.out.println(getByIndex(index));
    }
    public Entry getByIndex(int index){
        return (Entry) this.collection.values().toArray()[index];
    }

    public Collection<Entry> getAll(){
        return this.collection.values();
    }

    public Entry getById(String searchUuid){
        return this.collection.get(UUID.fromString(searchUuid));
    }

    public List<Entry> getWhere(String searchJsonString) {
        Set<Map.Entry<String, JsonElement>> searchMap = JsonParser.parseString(searchJsonString).getAsJsonObject().entrySet();
        
        Stream<Entry> resultEntryStream = this.collection.values().stream();

        for (Map.Entry<String, JsonElement> searchValue : searchMap) {
            resultEntryStream = resultEntryStream
                    .filter(entry -> entry.getJson().has(searchValue.getKey()))
                    .filter(entry -> entry.getJson().get(searchValue.getKey()).equals(searchValue.getValue()));
        }

        return resultEntryStream.collect(Collectors.toList());
    }

    public List<Entry> getWhereKeyExists(String... searchKeyStrings){
        Stream<Entry> resultEntryStream = this.collection.values().stream();
        for (String searchKeyString : searchKeyStrings) {
            resultEntryStream = resultEntryStream
                    .filter(entry -> entry.getJson().has(searchKeyString));
        }

        return resultEntryStream.collect(Collectors.toList());
    }

    public EntryList update(String searchJsonString, String newValueJsonString){
        List<Entry> entriesToEdit = getWhere(searchJsonString);
        for (Entry entryToDelete : entriesToEdit) {
            this.collection.get(entryToDelete.getUUID()).editEntry(newValueJsonString);
        }
        commitToFile();
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public Path getCollectionPath() {
        return collectionPath;
    }

    public EntryList removeEntryField(String searchJsonString, String... keysOfFieldToRemove) {
        List<Entry> entriesToRemoveFieldsFrom =  getWhere(searchJsonString);
        for (Entry entryToRemoveFieldsFrom : entriesToRemoveFieldsFrom) {
            for (String keyToRemove : keysOfFieldToRemove) {
                this.collection.get(entryToRemoveFieldsFrom.getUUID()).removeField(keyToRemove);
            }
        }
        commitToFile();
        return this;
    }

    public EntryList remove(String searchJsonString) {
        List<Entry> entriesToDelete = getWhere(searchJsonString);
        for (Entry entryToDelete : entriesToDelete) {
            this.collection.remove(entryToDelete.getUUID());
        }
        commitToFile();
        return this;
    }


    public void clear() {
        this.collection.clear();
        commitToFile();
    }

    public void delete() {
        try {
            Files.delete(this.collectionPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.collection = null;
        this.collectionPath = null;
        this.collectionName = null;
    }

    public int size(){
        return this.collection.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.collection.values().forEach(entry -> {
            stringBuilder.append(entry.getJson().toString());
            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }
}
