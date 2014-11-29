package uk.co.solong.steampojo.tf2.data;

import java.math.BigInteger;

import com.fasterxml.jackson.databind.JsonNode;

public class Attribute {
    private Long defIndex;
    private BigInteger value;
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
            value = valueNode.bigIntegerValue();
        }

        JsonNode floatValueNode = rootNode.path("floatValue");
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

    public BigInteger getValue() {
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
