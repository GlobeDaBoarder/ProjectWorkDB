package base.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public JsonObject getJson() {
        return fullJson;
    }

    //equals & hashCode


    @Override
    public String toString() {
        return "Entry{" +
                "fullJson=" + fullJson +
                '}';
    }
}
