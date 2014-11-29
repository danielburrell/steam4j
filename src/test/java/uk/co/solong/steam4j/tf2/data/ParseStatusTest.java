package uk.co.solong.steam4j.tf2.data;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import uk.co.solong.steam4j.tf2.data.items.Status;
import uk.co.solong.steam4j.tf2.data.items.TF2Backpack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseStatusTest {
    
    private TF2Backpack b;
    
    @Test
    public void shouldParseMissingParameter() throws JsonProcessingException, IOException {
        ObjectMapper m = new ObjectMapper();
        JsonNode n = m.readTree(
                getClass().getResourceAsStream("/parameterMissing.json"));
        b = new TF2Backpack(n, 4027L);
        assertEquals(new Long(4027),b.getSteamId());
        assertEquals(Status.STEAMID_INVALID_MISSING,b.getStatus());
    }
    
    @Test
    public void shouldParsePrivateProfile() throws JsonProcessingException, IOException {
        ObjectMapper m = new ObjectMapper();
        JsonNode n = m.readTree(
                getClass().getResourceAsStream("/privateProfile.json"));
        b = new TF2Backpack(n, 4027L);
        assertEquals(new Long(4027),b.getSteamId());
        assertEquals(Status.PRIVATE,b.getStatus());
    }

   
    

    
    


}
