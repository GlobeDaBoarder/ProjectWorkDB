package base.database;

import java.nio.file.Path;

public class AutoCommitCollection extends CollectionOfDatabaseAbstract {

    AutoCommitCollection(Path pathToColl) {
        super(pathToColl);
    }

    @Override
    public AutoCommitCollection useCollection(Path collectionPath) {
        super.useCollection(collectionPath);
        commitToFile();
        return this;
    }

    @Override
    protected void readEntryIntoCollection(String fullJson) {
        AutoCommitEntry existingEntry = new AutoCommitEntry(fullJson, this);
        this.collection.put(existingEntry.getUUID(), existingEntry);
    }

    @Override
    public AutoCommitCollection addEntry(String jsonBody) {
        AutoCommitEntry newEntry = new AutoCommitEntry(jsonBody, this);
        this.collection.put(newEntry.getUUID(), newEntry);
        commitToFile();
        return this;
    }

    @Override
    public AutoCommitCollection updateEntry(String searchJsonString, String newValueJsonString) {
        super.updateEntry(searchJsonString, newValueJsonString);
        commitToFile();
        return this;
    }

    @Override
    public AutoCommitCollection removeEntryField(String searchJsonString, String... keysOfFieldToRemove) {
        super.removeEntryField(searchJsonString, keysOfFieldToRemove);
        commitToFile();
        return this;
    }

    @Override
    public AutoCommitCollection removeEntry(String searchJsonString) {
        super.removeEntry(searchJsonString);
        commitToFile();
        return this;
    }

    @Override
    public void clear() {
        super.clear();
        commitToFile();
    }
}
