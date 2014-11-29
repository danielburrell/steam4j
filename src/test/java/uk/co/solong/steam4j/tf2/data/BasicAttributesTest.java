package uk.co.solong.steam4j.tf2.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import uk.co.solong.steam4j.tf2.data.items.TF2Backpack;
import uk.co.solong.steam4j.tf2.data.items.TF2Item;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasicAttributesTest {

    @Test
    public void shouldParseSenderOfGiftDataIncludingSteamIdAndAccountInfo() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper m = new ObjectMapper();
        JsonNode n = m.readTree(getClass().getResourceAsStream("/giftsender.json"));
        TF2Backpack b = new TF2Backpack(n, 4027L);
        Long actualSteamId = null;
        for (TF2Item item : b.getItems()) {
            if (item.getId().equals(139752071L)) {
                actualSteamId = item.getGifterSteamId();
            }
        }
        assertEquals(Long.valueOf(76561198032575096L), actualSteamId);
    }

    @Test
    public void shouldParseUncraftable() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper m = new ObjectMapper();
        JsonNode n = m.readTree(getClass().getResourceAsStream("/uncraftable.json"));
        TF2Backpack b = new TF2Backpack(n, 4027L);
        Boolean isCraftable = null;
        for (TF2Item item : b.getItems()) {
            if (item.getId().equals(139752071L)) {
                isCraftable = item.isCraftable();
            }
        }
        assertFalse(isCraftable);
    }

    @Test
    public void shouldParseCraftable() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper m = new ObjectMapper();
        JsonNode n = m.readTree(getClass().getResourceAsStream("/craftable.json"));
        TF2Backpack b = new TF2Backpack(n, 4027L);
        Boolean isCraftable = null;
        for (TF2Item item : b.getItems()) {
            if (item.getId().equals(139752071L)) {
                isCraftable = item.isCraftable();
            }
        }
        assertTrue(isCraftable);
    }

    @Test
    public void shouldParseUntradable() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper m = new ObjectMapper();
        JsonNode n = m.readTree(getClass().getResourceAsStream("/untradable.json"));
        TF2Backpack b = new TF2Backpack(n, 4027L);
        Boolean isTradable = null;
        for (TF2Item item : b.getItems()) {
            if (item.getId().equals(139752071L)) {
                isTradable = item.isTradable();
            }
        }
        assertFalse(isTradable);
    }

    @Test
    public void shouldParseTradable() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper m = new ObjectMapper();
        JsonNode n = m.readTree(getClass().getResourceAsStream("/tradable.json"));
        TF2Backpack b = new TF2Backpack(n, 4027L);
        Boolean isTradable = null;
        for (TF2Item item : b.getItems()) {
            if (item.getId().equals(139752071L)) {
                isTradable = item.isTradable();
            }
        }
        assertTrue(isTradable);
    }

    @Test
    public void shouldParseStyle() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper m = new ObjectMapper();
        JsonNode n = m.readTree(getClass().getResourceAsStream("/style.json"));
        TF2Backpack b = new TF2Backpack(n, 4027L);
        Integer style = null;
        for (TF2Item item : b.getItems()) {
            if (item.getId().equals(139752071L)) {
                style = item.getStyle();
            }
        }
        assertEquals(new Integer(5), style);
    }
}
