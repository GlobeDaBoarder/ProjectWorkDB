package base.entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

public class Entry {
    private final UUID objId;
    private JsonObject json;

    public Entry(String json) {
        this.objId = UUID.randomUUID();
        this.json = JsonParser.parseString(json).getAsJsonObject();
    }

    public Entry(JsonObject jsonObject) {
        this.objId = UUID.randomUUID();
        this.json = jsonObject;
    }

    public Entry() {
        this(new JsonObject());
    }

    public String getObjId() {
        return objId.toString();
    }

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject jsonObject) {
        this.json = jsonObject;
    }

    public void setJson(String json) {
        this.json = JsonParser.parseString(json).getAsJsonObject();
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
