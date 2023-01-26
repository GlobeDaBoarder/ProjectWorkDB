package base.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CollectionOfDatabaseAbstract implements CollectionOfDatabase {
    //make multi threaded
    protected Map<UUID, Entry> collection;
    protected String collectionName;
    protected Path collectionPath;

    CollectionOfDatabaseAbstract(Path pathToColl) {
        this.collection = new LinkedHashMap<>();
        this.collectionName = pathToColl.getFileName().toString();
        this.collectionPath = pathToColl;


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

    protected void loadCollection(Path collectionPath) {
        try (Stream<String> lines = Files.lines(collectionPath)) {
            lines
                    .forEach(this::readEntryIntoCollection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CollectionOfDatabase useCollection(Path collectionPath) {
        loadCollection(collectionPath);
        return this;
    }

    private void readEntryIntoCollection(String fullJson) {
        Entry existingEntry = Entry.readExistingEntry(fullJson);
        this.collection.put(existingEntry.getUUID(), existingEntry);
    }

    @Override
    public CollectionOfDatabase add(String jsonBody) {
        Entry newEntry = Entry.createEntry(jsonBody);
        this.collection.put(newEntry.getUUID(), newEntry);
        return this;
    }

    @Override
    public CollectionOfDatabase addAll(String... jsonBodies) {
        for (String jsonBody : jsonBodies) {
            add(jsonBody);
        }
        return this;
    }

    private void addToFile(Entry entry) {
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

    @Override
    public void commitToFile() {
        try (Writer writer = new FileWriter(collectionPath.toFile(), false)) {
            this.collection.values().forEach(this::addToFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CollectionOfDatabase printAll() {
        getAll().forEach(System.out::println);
        System.out.println('\n');
        return this;
    }

    @Override
    public void pintWithId(String searchUuid) {
        System.out.println(getById(searchUuid));
    }

    @Override
    public void printWithIndex(int index) {
        System.out.println(getByIndex(index));
    }

    @Override
    public Entry getByIndex(int index) {
        return (Entry) this.collection.values().toArray()[index];
    }

    @Override
    public Collection<Entry> getAll() {
        return this.collection.values();
    }

    @Override
    public Entry getById(String searchUuid) {
        return this.collection.get(UUID.fromString(searchUuid));
    }

    @Override
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

    @Override
    public List<Entry> getWhereKeyExists(String... searchKeyStrings) {
        Stream<Entry> resultEntryStream = this.collection.values().stream();
        for (String searchKeyString : searchKeyStrings) {
            resultEntryStream = resultEntryStream
                    .filter(entry -> entry.getJson().has(searchKeyString));
        }

        return resultEntryStream.collect(Collectors.toList());
    }

    @Override
    public CollectionOfDatabase update(String searchJsonString, String newValueJsonString) {
        List<Entry> entriesToEdit = getWhere(searchJsonString);
        for (Entry entryToDelete : entriesToEdit) {
            this.collection.get(entryToDelete.getUUID()).editEntry(newValueJsonString);
        }
        return this;
    }

    @Override
    public String getCollectionName() {
        return collectionName;
    }

    @Override
    public Path getCollectionPath() {
        return collectionPath;
    }

    @Override
    public CollectionOfDatabase removeEntryField(String searchJsonString, String... keysOfFieldToRemove) {
        List<Entry> entriesToRemoveFieldsFrom = getWhere(searchJsonString);
        for (Entry entryToRemoveFieldsFrom : entriesToRemoveFieldsFrom) {
            for (String keyToRemove : keysOfFieldToRemove) {
                this.collection.get(entryToRemoveFieldsFrom.getUUID()).removeField(keyToRemove);
            }
        }
        return this;
    }

    @Override
    public CollectionOfDatabase remove(String searchJsonString) {
        List<Entry> entriesToDelete = getWhere(searchJsonString);
        for (Entry entryToDelete : entriesToDelete) {
            this.collection.remove(entryToDelete.getUUID());
        }
        return this;
    }

    @Override
    public void clear() {
        this.collection.clear();
    }

    @Override
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

    @Override
    public int size() {
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
