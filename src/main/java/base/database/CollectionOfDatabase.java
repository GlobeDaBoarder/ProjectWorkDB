package base.database;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface CollectionOfDatabase {
    CollectionOfDatabase useCollection(Path collectionPath);

    CollectionOfDatabase add(String jsonBody);

    CollectionOfDatabase addAll(String... jsonBodies);

    void commitToFile();

    CollectionOfDatabase printAll();

    void pintWithId(String searchUuid);

    void printWithIndex(int index);

    Entry getByIndex(int index);

    Collection<Entry> getAll();

    Entry getById(String searchUuid);

    List<Entry> getWhere(String searchJsonString);

    List<Entry> getWhereKeyExists(String... searchKeyStrings);

    CollectionOfDatabase update(String searchJsonString, String newValueJsonString);

    String getCollectionName();

    Path getCollectionPath();

    CollectionOfDatabase removeEntryField(String searchJsonString, String... keysOfFieldToRemove);

    CollectionOfDatabase remove(String searchJsonString);

    void clear();

    void delete();

    int size();
}
