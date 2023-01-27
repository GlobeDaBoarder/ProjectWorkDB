package base.database;

class AutoCommitEntry extends Entry {

    private final AutoCommitCollection parentCollection;
    public AutoCommitEntry(String jsonBody, AutoCommitCollection parentCollection) {
        super(jsonBody);
        this.parentCollection = parentCollection;
    }

    @Override
    public void editEntry(String newValueJsonString) {
        super.editEntry(newValueJsonString);
        this.parentCollection.commitToFile();
    }

    @Override
    public void removeField(String keyToRemove) {
        super.removeField(keyToRemove);
        this.parentCollection.commitToFile();
    }
}
