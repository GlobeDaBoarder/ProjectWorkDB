package base.database;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

abstract public class Entry {
    protected final JsonObject fullJson;

    public Entry(String jsonBody) {
        this.fullJson = JsonParser.parseString(prependId(
                UUID.randomUUID(),
                jsonBody
        )).getAsJsonObject();
    }

    protected static String prependId(UUID uuid, String jsonParams) {
        return "{\"id\" : \"" +
                uuid +
                "\", " +
                jsonParams.substring(1);
    }

    public UUID getUUID() {
        return UUID.fromString(this.fullJson.get("id").getAsString());
    }

    JsonObject getJson() {
        return fullJson;
    }

    public void editEntry(String newValueJsonString) {
        Set<Map.Entry<String, JsonElement>> newValuesSet = JsonParser.parseString(newValueJsonString).getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> newValue : newValuesSet) {
            this.fullJson.add(newValue.getKey(), newValue.getValue());
        }
    }

    public void removeField(String keyToRemove) {
        this.fullJson.remove(keyToRemove);
    }

    public String toString() {
        return "Entry{" +
                "fullJson=" + fullJson +
                '}';
    }
}
