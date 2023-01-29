package base.database;

import com.google.gson.JsonObject;

import java.util.UUID;

public interface Entry {
    UUID getUUID();

    JsonObject getJson();

    void editEntry(String newValueJsonString);

    void removeField(String keyToRemove);
}
