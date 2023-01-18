package base.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

public class Entry {


    private final UUID objId;
    private JsonObject json;

    public Entry(String json) {
        this.objId = UUID.randomUUID();
        //isValidJson(json);
        this.json = setJson(json);
    }

    //constructor with Object

    public Entry() {
        this("");
    }

    public UUID getObjId() {
        return objId;
    }

    public JsonObject getJson() {
        return json;
    }


    public JsonObject setJson(String json) {
        json = prependId(json);
        return JsonParser.parseString(json).getAsJsonObject();
    }

    private String prependId(String json) {
        return "{\"id\" : \"" +
                this.objId +
                "\", " +
                json.substring(1);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "objId=" + objId +
                ", json=" + json.toString() +
                '}';
    }

    //equals & hashCode


}
