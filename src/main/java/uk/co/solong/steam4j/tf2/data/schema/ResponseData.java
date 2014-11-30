package uk.co.solong.steam4j.tf2.data.schema;

import com.fasterxml.jackson.databind.JsonNode;

public class ResponseData {
    private JsonNode data;
    private long lastModified;
    public JsonNode getData() {
        return data;
    }
    public void setData(JsonNode data) {
        this.data = data;
    }
    public long getLastModified() {
        return lastModified;
    }
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
