package uk.co.solong.steam4j.tf2.data.items;

import com.fasterxml.jackson.databind.JsonNode;

public class Attribute {
    private Long defIndex;
    private Long value;
    private String floatValue;
    private Long steamId;
    private String steamDisplayName;

    public Attribute(JsonNode rootNode) {

        JsonNode defindexNode = rootNode.path("defindex");
        if (!defindexNode.isMissingNode()) {
            defIndex = defindexNode.asLong();
        }

        JsonNode valueNode = rootNode.path("value");
        if (!valueNode.isMissingNode()) {
            value = valueNode.asLong();
        }

        JsonNode floatValueNode = rootNode.path("float_value");
        if (!floatValueNode.isMissingNode()) {
            floatValue = floatValueNode.asText();
        }

        JsonNode accountInfoNode = rootNode.path("account_info");
        if (!accountInfoNode.isMissingNode()) {
            JsonNode steamidNode = accountInfoNode.path("steamid");
            if (!steamidNode.isMissingNode()) {
                steamId = steamidNode.asLong();
            }
            JsonNode personanameNode = accountInfoNode.path("personaname");
            if (!personanameNode.isMissingNode()) {
                steamDisplayName = personanameNode.asText();
            }
        }

    }

    public Long getDefIndex() {
        return defIndex;
    }

    public Long getValue() {
        return value;
    }

    public String getFloatValue() {
        return floatValue;
    }

    public Long getSteamId() {
        return steamId;
    }

    public String getSteamDisplayName() {
        return steamDisplayName;
    }
}
