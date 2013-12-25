package uk.co.solong.tf2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import uk.co.solong.tf2.Attribute;
import uk.co.solong.tf2.Backpack;
import uk.co.solong.tf2.Item;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDeserialise {

	@Test
	public void shouldParseSenderOfGiftData() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		Backpack b = m.readValue(getClass().getResourceAsStream("/4027.json"), Backpack.class);
		for (Item item : b.getResult().getItems()){
			if (item.getId().equals(709280078L)) {
				for (Attribute attribute: item.getAttributes()) {
					if (attribute.getDefindex().equals(372)){
						assertEquals(attribute.getAccountInfo().getSteamid(), Long.valueOf(76561198032575096L));
						assertEquals(attribute.getAccountInfo().getSteamid(), Long.valueOf("Timber"));
					}
				}
			}
		}
		assertTrue(b.getResult().getItems().size() > 1);
	}

}
