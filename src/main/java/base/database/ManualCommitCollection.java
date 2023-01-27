package base.database;

import java.nio.file.Path;

public class ManualCommitCollection extends CollectionOfDatabaseAbstract{
    ManualCommitCollection(Path pathToColl) {
        super(pathToColl);
    }

    @Override
    public ManualCommitCollection useCollection(Path collectionPath) {
        super.useCollection(collectionPath);
        return this;
    }

    @Override
    protected void readEntryIntoCollection(String fullJson) {
        ManualCommitEntry existingEntry = new ManualCommitEntry(fullJson);
        this.collection.put(existingEntry.getUUID(), existingEntry);
    }

    @Override
    public ManualCommitCollection addEntry(String jsonBody) {
        ManualCommitEntry newEntry = new ManualCommitEntry(jsonBody);
        this.collection.put(newEntry.getUUID(), newEntry);
        return this;
    }

    @Override
    public ManualCommitCollection addAllEntries(String... jsonBodies) {
        super.addAllEntries(jsonBodies);
        return this;
    }

    @Override
    public ManualCommitCollection updateEntry(String searchJsonString, String newValueJsonString) {
        super.updateEntry(searchJsonString, newValueJsonString);
        return this;
    }

    @Override
    public ManualCommitCollection removeEntryField(String searchJsonString, String... keysOfFieldToRemove) {
        super.removeEntryField(searchJsonString, keysOfFieldToRemove);
        return this;
    }

    @Override
    public ManualCommitCollection removeEntry(String searchJsonString) {
        super.removeEntry(searchJsonString);
        return this;
    }
}
