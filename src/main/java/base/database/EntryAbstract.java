package base.database;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

abstract class EntryAbstract implements Entry {
    protected final JsonObject fullJson;

    public EntryAbstract(String jsonBody) {
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

    @Override
    public UUID getUUID() {
        return UUID.fromString(this.fullJson.get("id").getAsString());
    }

    @Override
    public JsonObject getJson() {
        return fullJson.deepCopy();
    }

    @Override
    public void editEntry(String newValueJsonString) {
        Set<Map.Entry<String, JsonElement>> newValuesSet = JsonParser.parseString(newValueJsonString).getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> newValue : newValuesSet) {
            this.fullJson.add(newValue.getKey(), newValue.getValue());
        }
    }

    @Override
    public void removeField(String keyToRemove) {
        this.fullJson.remove(keyToRemove);
    }

    @Override
    public Object asObject(Class<?> clazz) {
        return new Gson().fromJson(this.fullJson, clazz);
    }

    public String toString() {
        return "Entry{" +
                "fullJson=" + fullJson +
                '}';
    }
}
