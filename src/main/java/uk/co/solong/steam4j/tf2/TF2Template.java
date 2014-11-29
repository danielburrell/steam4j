package uk.co.solong.steam4j.tf2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import uk.co.solong.steam4j.tf2.data.TF2Backpack;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TF2Template {
    private static final Logger logger = LoggerFactory.getLogger(TF2Template.class);

    private static final String DEFAULT_BASE_URL = "http://api.steamcommunity.com";
    private final String apiKey;
    private final String apiUrlTemplate;
    private static final String apiGetPlayerItems = "/IEconItems_440/GetPlayerItems/v0001/?key={key}&steamId={steamId}";

    public TF2Template(String apiKey) {
        this(apiKey, DEFAULT_BASE_URL);
    }

    public TF2Template(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.apiUrlTemplate = baseUrl + apiGetPlayerItems.replace("{key}", apiKey);
    }

    public TF2Backpack getPlayerItems(final Long steamId) {
        RetryTemplate template = createRetryTemplate();
        final TF2Backpack backpack = template.execute(new RetryCallback<TF2Backpack, RuntimeException>() {
            public TF2Backpack doWithRetry(RetryContext context) {
                Reader streamReader = null;
                try {
                    logWarningIfThisIsARetryAttempt(context);
                    InputStream input = new URL(apiUrlTemplate.replace("{steamId}", steamId.toString())).openStream();
                    streamReader = new InputStreamReader(input, "UTF-8");
                    ObjectMapper m = new ObjectMapper();
                    JsonNode jsonBackpack = m.readTree(streamReader);
                    TF2Backpack backpack = new TF2Backpack(jsonBackpack, steamId);
                    return backpack;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        streamReader.close();
                    } catch (IOException e) {
                        // couldn't close
                    }
                }
            }

            private void logWarningIfThisIsARetryAttempt(RetryContext context) {
                if (context.getRetryCount() > 0) {
                    logger.warn("Problem fetching steamId {}. (Retry attempt:{})", steamId, context.getRetryCount());
                } else {
                    logger.debug("Calling steam for {}", steamId);
                }
            }
        });
        return backpack;
    }

    private RetryTemplate createRetryTemplate() {
        RetryTemplate template = new RetryTemplate();
        FixedBackOffPolicy bop = new FixedBackOffPolicy();
        bop.setBackOffPeriod(60000L);
        template.setBackOffPolicy(bop);
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        policy.setMaxAttempts(3);
        template.setRetryPolicy(policy);
        return template;
    }

    String getApiKey() {
        return apiKey;
    }

}
