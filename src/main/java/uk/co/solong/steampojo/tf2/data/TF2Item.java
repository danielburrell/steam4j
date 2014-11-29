package uk.co.solong.steampojo.tf2.data;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class TF2Item {
    private Long id;
    private Long originalId;
    private Long defIndex;
    private Integer level;
    private Integer quantity;
    private Integer origin;
    private Boolean isTradable;
    private Boolean isCraftable;
    private Integer quality;
    private String customName;
    private String customDescription;
    private TF2Item subItem;
    private Integer style;
    private List<Attribute> attributes;

    // private attributes
    public TF2Item(JsonNode rootNode) {
        JsonNode idNode = rootNode.path("id");
        if (!idNode.isMissingNode()) {
            id = idNode.asLong();
        }
        JsonNode originalIdNode = rootNode.path("original_id");
        if (!originalIdNode.isMissingNode()) {
            originalId = originalIdNode.asLong();
        }
        JsonNode defIndexNode = rootNode.path("defindex");
        if (!defIndexNode.isMissingNode()) {
            defIndex = defIndexNode.asLong();
        }
        JsonNode levelNode = rootNode.path("level");
        if (!levelNode.isMissingNode()) {
            level = levelNode.asInt();
        }
        JsonNode quantityNode = rootNode.path("quantity");
        if (!quantityNode.isMissingNode()) {
            quantity = quantityNode.asInt();
        }
        JsonNode originNode = rootNode.path("origin");
        if (!originNode.isMissingNode()) {
            origin = originNode.asInt();
        }

        isTradable = !rootNode.path("flag_cannot_trade").asBoolean(false);
        isCraftable = !rootNode.path("flag_cannot_craft").asBoolean(false);

        JsonNode qualityNode = rootNode.path("quality");
        if (!qualityNode.isMissingNode()) {
            quality = qualityNode.asInt();
        }
        JsonNode customNameNode = rootNode.path("customName");
        if (!customNameNode.isMissingNode()) {
            customName = customNameNode.asText();
        }
        JsonNode customDescriptionNode = rootNode.path("customDescription");
        if (!customDescriptionNode.isMissingNode()) {
            customDescription = customDescriptionNode.asText();
        }
        JsonNode styleNode = rootNode.path("style");
        if (!styleNode.isMissingNode()) {
            style = styleNode.asInt();
        }
        JsonNode containedItemNode = rootNode.path("contained_item");
        if (!containedItemNode.isMissingNode()) {
            subItem = new TF2Item(containedItemNode);
        }

        attributes = new LinkedList<Attribute>();
        JsonNode attributesNode = rootNode.path("attributes");
        if (!attributesNode.isMissingNode()) {
            for (final JsonNode attributeNode : attributesNode) {
                attributes.add(new Attribute(attributeNode));
            }
        }
    }

    public Long getId() {
        return id;
    }

    public Long getOriginalId() {
        return originalId;
    }

    public Long getDefIndex() {
        return defIndex;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getOrigin() {
        return origin;
    }

    public Boolean isTradable() {
        return isTradable;
    }

    public Boolean isCraftable() {
        return isCraftable;
    }

    public Integer getQuality() {
        return quality;
    }

    public String getCustomName() {
        return customName;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public TF2Item getSubItem() {
        return subItem;
    }

    public Integer getStyle() {
        return style;
    }

    public Long getSeries() {
        for (Attribute attribute : attributes) {
            if (AttributeDef.SERIES.getDefindex().equals(attribute.getDefIndex())) {
                return Long.parseLong(attribute.getFloatValue());
            }
        }
        return null;
    }

    public Boolean isGiftWrapped() {
        for (Attribute attribute : attributes) {
            if (AttributeDef.GIFTWRAPPED.getDefindex().equals(attribute.getDefIndex())) {
                return true;
            }
        }
        return false;
    }

    public BigInteger getCraftNumber() {
        for (Attribute attribute : attributes) {
            if (AttributeDef.CRAFT_NUMBER.getDefindex().equals(attribute.getDefIndex())) {
                return attribute.getValue();
            }
        }
        return null;
    }

    public Boolean isCraftNumberPresent() {
        for (Attribute attribute : attributes) {
            if (AttributeDef.CRAFT_NUMBER.getDefindex().equals(attribute.getDefIndex())) {
                return true;
            }
        }
        return false;
    }

    public Long getGifterSteamId() {
        for (Attribute attribute : attributes) {
            if (AttributeDef.GIFTWRAPPED.getDefindex().equals(attribute.getDefIndex())) {
                return attribute.getSteamId();
            }
        }
        return null;
    }
}
