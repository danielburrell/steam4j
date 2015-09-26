package uk.co.solong.steam4j.tf2.data.schema;

import com.fasterxml.jackson.databind.JsonNode;

public class TF2SchemaItem {
    private Long defIndex;
    private String name;
    private String image;
    private JsonNode rawData;

    public TF2SchemaItem(JsonNode item) {
        this.defIndex = item.path("defindex").asLong();
        this.name = item.path("item_name").asText();
        this.image = item.path("image_url").asText();
        this.rawData = item;
    }

    public TF2SchemaItem() {
    }

    public long getDefIndex() {
        return defIndex;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setDefIndex(Long defIndex) {
        this.defIndex = defIndex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public JsonNode getRawData() {
        return rawData;
    }

    public void setRawData(JsonNode rawData) {
        this.rawData = rawData;
    }

    public Boolean isCrate() {
        return "supply_crate".equals(rawData.path("craft_class").asText(""));
    }
    
    public Integer getCrateSeries() {
        for (JsonNode attribute: rawData.path("attributes")) {
            if ("supply_crate_series".equals(attribute.get("class").asText(""))){
                return attribute.get("value").asInt();
            }
        }
        return null;
    }

}
