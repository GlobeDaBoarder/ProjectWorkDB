package base.database;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface CollectionOfDatabase {
    CollectionOfDatabase useCollection(Path collectionPath);

    CollectionOfDatabase addEntry(String jsonBody);

    CollectionOfDatabase addEntry(Object serializableObject);

    CollectionOfDatabase addAllEntries(String... jsonBodies);

    void commitToFile();

    void printAll();

    void pintWithId(String searchUuid);

    void printWithIndex(int index);

    Entry getByIndex(int index);

    Collection<Entry> getAll();

    Entry getById(String searchUuid);

    List<Entry> getWhere(String searchJsonString);

    List<Entry> getWhereKeyExists(String... searchKeyStrings);

    CollectionOfDatabase updateEntry(String searchJsonString, String newValueJsonString);

    String getCollectionName();

    Path getCollectionPath();

    CollectionOfDatabase removeEntryField(String searchJsonString, String... keysOfFieldToRemove);

    CollectionOfDatabase removeEntry(String searchJsonString);

    void clear();

    int size();
}
