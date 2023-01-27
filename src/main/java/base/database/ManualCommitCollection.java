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
    public CollectionOfDatabase addEntry(String jsonBody) {
        ManualCommitEntry newEntry = new ManualCommitEntry(jsonBody);
        this.collection.put(newEntry.getUUID(), newEntry);
        return this;
    }


}
