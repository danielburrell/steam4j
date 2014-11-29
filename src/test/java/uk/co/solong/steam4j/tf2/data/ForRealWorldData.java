package uk.co.solong.steam4j.tf2.data;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.solong.steam4j.tf2.data.Status;
import uk.co.solong.steam4j.tf2.data.TF2Backpack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ForRealWorldData {
    
    private TF2Backpack b;
    
    @Before
    public void parseSteamData() throws JsonProcessingException, IOException {
        ObjectMapper m = new ObjectMapper();
        JsonNode n = m.readTree(
                getClass().getResourceAsStream("/4027.json"));
        b = new TF2Backpack(n, 4027L);
    }

    @After
    public void resetData() {
        b = null;
    }
    
    @Test
    public void shouldSetSteamId() throws JsonProcessingException, IOException {
        assertEquals(new Long(4027),b.getSteamId());
    }
    
    @Test
    public void shouldSetBackpackSlots() throws JsonProcessingException, IOException {
        assertEquals(2000,b.getBackpackSlots());
    }
    
    @Test
    public void shouldSetStatus() throws JsonProcessingException, IOException {
        assertEquals(Status.SUCCESS,b.getStatus());
    }
    
    @Test
    public void shouldContain1867Items() throws JsonProcessingException, IOException {
        assertEquals(1867,b.getItems().size());
    }
    

    
    


}
