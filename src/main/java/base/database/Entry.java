package base.database;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Entry {

    private final JsonObject fullJson;

    static Entry createEntry(String jsonParams){
        return new Entry(
                prependId(
                        UUID.randomUUID(),
                        jsonParams
                )
        );
    }

    private static String prependId(UUID uuid, String jsonParams) {
        return "{\"id\" : \"" +
                uuid +
                "\", " +
                jsonParams.substring(1);
    }

    static Entry readExistingEntry(String fullJson){
        return new Entry(fullJson);
    }

    private Entry(String fullJson) {
        //isValidJson(json);
        this.fullJson = JsonParser.parseString(fullJson).getAsJsonObject();
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
//            if(this.fullJson.has(newValue.getKey())){
//                this.fullJson.add(newValue.getKey(), newValue.getValue());
//            }
//            else {
//                this.fullJson.add();
//            }
            this.fullJson.add(newValue.getKey(), newValue.getValue());
        }
    }

    public void removeField(String keyToRemove) {
        this.fullJson.remove(keyToRemove);
    }


    //equals & hashCode


    @Override
    public String toString() {
        return "Entry{" +
                "fullJson=" + fullJson +
                '}';
    }
}
