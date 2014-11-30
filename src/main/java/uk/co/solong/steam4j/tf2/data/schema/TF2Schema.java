package uk.co.solong.steam4j.tf2.data.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import uk.co.solong.steam4j.tf2.data.items.TF2Item;

import com.fasterxml.jackson.databind.JsonNode;

public class TF2Schema {

    private final JsonNode root;

    public TF2Schema(JsonNode root) {
        this.root = root;
    }

    /**
     * 
     * @return a list of TF2SchemaItems representing containing only name and defindex
     *         items.
     */
    public List<TF2SchemaItem> getItemsWithNameAndDefIndex() {
        JsonNode items = root.path("result").path("items");
        if (!items.isMissingNode()) {
            List<TF2SchemaItem> result = new ArrayList<TF2SchemaItem>(items.size());
            for (JsonNode item : items) {
                TF2SchemaItem itemCandidate = new TF2SchemaItem();
                itemCandidate.setDefIndex(item.path("defindex").asLong());
                itemCandidate.setName(item.path("item_name").asText());
                result.add(itemCandidate);
            }
            return result;
        } else {
            return new ArrayList<TF2SchemaItem>(0);
        }
    }
    
    /**
     * 
     * @return a list of TF2SchemaItems representing containing only name and defindex
     *         items.
     */
    public List<TF2SchemaItem> getItemsWithNameAndDefIndexAndImage() {
        JsonNode items = root.path("result").path("items");
        if (!items.isMissingNode()) {
            List<TF2SchemaItem> result = new ArrayList<TF2SchemaItem>(items.size());
            for (JsonNode item : items) {
                TF2SchemaItem itemCandidate = new TF2SchemaItem();
                itemCandidate.setDefIndex(item.path("defindex").asLong());
                itemCandidate.setName(item.path("item_name").asText());
                itemCandidate.setImage(item.path("image_url").asText());
                result.add(itemCandidate);
            }
            return result;
        } else {
            return new ArrayList<TF2SchemaItem>(0);
        }
    }
    
    public Map<Integer, TF2SchemaQuality> getCompleteQualityMap() {
        JsonNode qualitiesNode = root.path("result").path("qualities");
        JsonNode qualityNamesNode = root.path("result").path("qualityNames");
        if (!qualitiesNode.isMissingNode()) {
            Map<Integer,TF2SchemaQuality> qualityMap = new HashMap<Integer,TF2SchemaQuality>();
            Iterator<Entry<String, JsonNode>> i = qualitiesNode.fields();
            while (i.hasNext()) {
                Entry<String,JsonNode> qualityEntry = i.next();
                int id = qualityEntry.getValue().asInt();
                String internalname = qualityEntry.getKey();
                TF2SchemaQuality quality = new TF2SchemaQuality(id,qualityNamesNode.path(internalname).asText());
                qualityMap.put(id, quality);
            }
            return qualityMap;
        } else {
            return null;
        }
    }
    
    public Map<Integer, TF2SchemaQuality> getActiveQualityMap() {
        Map<Integer, TF2SchemaQuality> qualityMap = getCompleteQualityMap();
        List<Integer> removables = new LinkedList<Integer>();
        for (Integer id : qualityMap.keySet()) {
            String candidate = qualityMap.get(id).getName();
            if (Character.isLowerCase(candidate.charAt(0)) && (Character.isDigit(candidate.charAt(candidate.length()-1)))){
                removables.add(id);
            }
        }
        for (int removable: removables){
            qualityMap.remove(removable);
        }
        return qualityMap;
    }
    
    public JsonNode getRawData() {
        return root;
    }
}
