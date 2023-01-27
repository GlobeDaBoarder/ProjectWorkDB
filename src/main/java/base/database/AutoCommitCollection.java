package base.database;

import java.nio.file.Path;

public class AutoCommitCollection extends CollectionOfDatabaseAbstract {

    AutoCommitCollection(Path pathToColl) {
        super(pathToColl);
    }

    @Override
    public CollectionOfDatabase useCollection(Path collectionPath) {
        CollectionOfDatabase result = super.useCollection(collectionPath);
        commitToFile();
        return result;
    }

    @Override
    public CollectionOfDatabase addEntry(String jsonBody) {
        CollectionOfDatabase result = super.addEntry(jsonBody);
        commitToFile();
        return result;
    }

    @Override
    public CollectionOfDatabase updateEntry(String searchJsonString, String newValueJsonString) {
        CollectionOfDatabase result = super.updateEntry(searchJsonString, newValueJsonString);
        commitToFile();
        return result;
    }

    @Override
    public CollectionOfDatabase removeEntryField(String searchJsonString, String... keysOfFieldToRemove) {
        CollectionOfDatabase result = super.removeEntryField(searchJsonString, keysOfFieldToRemove);
        commitToFile();
        return result;
    }

    @Override
    public CollectionOfDatabase removeEntry(String searchJsonString) {
        CollectionOfDatabase result =  super.removeEntry(searchJsonString);
        commitToFile();
        return result;
    }

    @Override
    public void clear() {
        super.clear();
        commitToFile();
    }
}
