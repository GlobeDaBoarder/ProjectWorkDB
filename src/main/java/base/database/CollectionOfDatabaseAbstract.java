package base.database;

import base.annotation.JsonSerializationException;
import base.annotation.ObjectToJsonConverter;
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

abstract class CollectionOfDatabaseAbstract implements CollectionOfDatabase {
    //make multi threaded
    protected final Map<UUID, Entry> collection;
    protected final String collectionName;
    protected final Path collectionPath;

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

    protected abstract void readEntryIntoCollection(String fullJson);

    @Override
    abstract public CollectionOfDatabase addEntry(String jsonBody);

    @Override
    public CollectionOfDatabase addEntry(Object serializableObject) {
        try {
            String jsonBody = new ObjectToJsonConverter().convertToJson(serializableObject);
            addEntry(jsonBody);
        } catch (JsonSerializationException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public CollectionOfDatabase addAllEntries(String... jsonBodies) {
        for (String jsonBody : jsonBodies) {
            addEntry(jsonBody);
        }
        return this;
    }

    @Override
    public CollectionOfDatabase addAllEntries(Object... serializableObjects) {
        for (Object serializableObject : serializableObjects) {
            addEntry(serializableObject);
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
    public void printAll() {
        System.out.println(this);
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
    public List<Entry> getAll() {
        return this.collection.values().stream().toList();
    }

    @Override
    public List<Object> getAllAsObject(Class<?> clazz) {
        return this.collection.values()
                .stream()
                .map(entry -> entry.asObject(clazz))
                .toList();
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
    public CollectionOfDatabase updateEntry(String searchJsonString, String newValueJsonString) {
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
    public CollectionOfDatabase removeEntry(String searchJsonString) {
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
