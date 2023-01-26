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
    public CollectionOfDatabase add(String jsonBody) {
        CollectionOfDatabase result = super.add(jsonBody);
        commitToFile();
        return result;
    }

    @Override
    public CollectionOfDatabase update(String searchJsonString, String newValueJsonString) {
        CollectionOfDatabase result = super.update(searchJsonString, newValueJsonString);
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
    public CollectionOfDatabase remove(String searchJsonString) {
        CollectionOfDatabase result =  super.remove(searchJsonString);
        commitToFile();
        return result;
    }

    @Override
    public void clear() {
        super.clear();
        commitToFile();
    }
}
