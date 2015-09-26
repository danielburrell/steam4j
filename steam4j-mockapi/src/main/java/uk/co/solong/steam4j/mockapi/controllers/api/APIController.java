package uk.co.solong.steam4j.mockapi.controllers.api;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * This controller returns the list of all tf2 items (id, name) so that the view
 * can be populated. The schema is ultimately sourced from schema.tf. if this
 * request fails, the schemaUnavailable result is sent instead.
 * 
 * @author Daniel Burrell
 *
 */
@RestController
@RequestMapping("/IEconItems_440")
public class APIController {
    /*
     * private final SchemaProvider schemaProvider;
     */
    @RequestMapping("/GetPlayerItems/v0001/")
    public JsonNode getPlayerItems(String key, Long steamId) throws JsonProcessingException, IOException {
        ObjectMapper m = new ObjectMapper();
        return m.readTree(new ClassPathResource("/assets/4027.json").getInputStream());//new File(getClass().getResource("").getFile()));
    }

    /*
     * @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
     * 
     * @ExceptionHandler(SchemaUnavailableException.class) public @ResponseBody
     * ErrorResult schemaUnavailable() { ErrorResult errorResult = new
     * ErrorResult();
     * errorResult.setId(ErrorCodes.SCHEMA_LOOKUP_FAILED.getId()); return
     * errorResult; }
     */
    public APIController(/* SchemaProvider schemaProvider */) {
        /* this.schemaProvider = schemaProvider; */
    }

}