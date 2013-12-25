package uk.co.solong.tf2;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDeserialise {

	@Test
	public void shouldParsePrivateProfiles() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		Backpack b = m.readValue(
				getClass().getResourceAsStream("/parameterMissing.json"),
				Backpack.class);
		Long actualStatus = null;
		String actualStatusDetail = null;
		actualStatus = b.getResult().getStatus();
		actualStatusDetail = b.getResult().getStatusDetail();
		assertEquals(Long.valueOf(8L), actualStatus);
		assertEquals("SteamID parameter was missing", actualStatusDetail);
	}

	@Test
	public void shouldParseWhenSteamParameterIsMissing()
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		Backpack b = m.readValue(
				getClass().getResourceAsStream("/privateProfile.json"),
				Backpack.class);
		Long actualStatus = null;
		String actualStatusDetail = null;
		actualStatus = b.getResult().getStatus();
		actualStatusDetail = b.getResult().getStatusDetail();
		assertEquals(Long.valueOf(15L), actualStatus);
		assertEquals("Permission denied", actualStatusDetail);
	}

	@Test
	public void shouldParseSenderOfGiftDataIncludingSteamIdAndAccountInfo()
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		Backpack b = m.readValue(getClass().getResourceAsStream("/4027.json"),
				Backpack.class);
		Long actualSteamId = null;
		String actualPersonaname = null;
		for (Item item : b.getResult().getItems()) {
			if (item.getId().equals(709280078L)) {
				for (Attribute attribute : item.getAttributes()) {
					if (attribute.getDefindex().equals(372L)) {
						actualSteamId = attribute.getAccountInfo().getSteamid();
						actualPersonaname = attribute.getAccountInfo()
								.getPersonaname();
					}
				}
			}
		}
		assertEquals(Long.valueOf(76561198032575096L), actualSteamId);
		assertEquals("Timber", actualPersonaname);
	}

	@Test
	public void shouldParseUncraftable() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		Backpack b = m.readValue(getClass().getResourceAsStream("/uncraftable.json"),
				Backpack.class);
		Boolean flagCannotCraft = null;
		for (Item item : b.getResult().getItems()) {
			if (item.getId().equals(139752071L)) {
						flagCannotCraft = item.getFlagCannotCraft();
			}
		}
		assertEquals(true, flagCannotCraft);
	}
	
	@Test
	public void shouldParseUntradable() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		Backpack b = m.readValue(getClass().getResourceAsStream("/untradable.json"),
				Backpack.class);
		Boolean flagCannotTrade = null;
		for (Item item : b.getResult().getItems()) {
			if (item.getId().equals(139752071L)) {
						flagCannotTrade = item.getFlagCannotTrade();
			}
		}
		assertEquals(true, flagCannotTrade);
	}
	
	@Test
	public void shouldParseStyle() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		Backpack b = m.readValue(getClass().getResourceAsStream("/style.json"),
				Backpack.class);
		Long style = null;
		for (Item item : b.getResult().getItems()) {
			if (item.getId().equals(139752071L)) {
				style = item.getStyle();
			}
		}
		assertEquals(Long.valueOf(5L), style);
	}
}
