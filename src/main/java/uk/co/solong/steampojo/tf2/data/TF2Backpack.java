package uk.co.solong.steampojo.tf2.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class TF2Backpack {
    private final Status status;
    private final Long steamId;
    private final int backpackSlots;
    private final List<TF2Item> items;

    /**
     * Creates a new TF2Backpack given an unmodified JSON representation from the Steam GetPlayerItems api call.
     * It is necessary to provide the steamId, since the steamId is not recorded within the backpack data.
     * @param rootNode
     * @param steamId
     */
    public TF2Backpack(JsonNode rootNode, Long steamId) {
        this.steamId = steamId;
        JsonNode resultNode = rootNode.path("result");
        if (!resultNode.isMissingNode()) {
            JsonNode status = resultNode.path("status");
            this.status = Status.fromState(status.asInt(-1));
            if (this.status.equals(Status.SUCCESS)) {
                JsonNode backpackSlotsNode = resultNode.path("num_backpack_slots");
                this.backpackSlots = backpackSlotsNode.asInt(0);
                this.items = new ArrayList<TF2Item>(backpackSlots);
                JsonNode itemsNode = resultNode.path("items");
                for (final JsonNode itemNode : itemsNode) {
                    items.add(new TF2Item(itemNode));
                }
            } else {
                backpackSlots = 0;
                this.items = new ArrayList<TF2Item>(0);
            }
        } else {
            status = Status.UNKNOWN_STATUS;
            backpackSlots = 0;
            this.items = new ArrayList<TF2Item>(0);
        }
    }

    public Long getSteamId() {
        return steamId;
    }

    public Status getStatus() {
        return status;
    }

    public int getBackpackSlots() {
        return backpackSlots;
    }

    public List<TF2Item> getItems() {
        return items;
    }
}
