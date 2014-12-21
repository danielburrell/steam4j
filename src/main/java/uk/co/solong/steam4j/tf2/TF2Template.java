package uk.co.solong.steam4j.tf2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import uk.co.solong.steam4j.tf2.data.items.TF2Backpack;
import uk.co.solong.steam4j.tf2.data.schema.ResponseData;
import uk.co.solong.steam4j.tf2.data.schema.TF2Schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TF2Template {
    private final class SchemaRequestCallback implements RequestCallback {
        @Override
        public void doWithRequest(ClientHttpRequest arg0) throws IOException {
            arg0.getHeaders().setIfModifiedSince(lastModified);
        }
    }

    private final class SchemaResponseExtractor implements ResponseExtractor<ResponseData> {
        @Override
        public ResponseData extractData(ClientHttpResponse response) throws IOException {

            if (response.getStatusCode() == HttpStatus.OK) {
                InputStream inputStream = null;
                try {
                    inputStream = response.getBody();
                    Reader streamReader = new InputStreamReader(inputStream, "UTF-8");
                    ObjectMapper m = new ObjectMapper();
                    JsonNode jsonBackpack = m.readTree(streamReader);
                    ResponseData resultData = new ResponseData();
                    resultData.setData(jsonBackpack);
                    resultData.setLastModified(response.getHeaders().getLastModified());
                    return resultData;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } else if (response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
                return null;
            } else {
                throw new RuntimeException("SteamAPI returned non-200 status code.{}" + response.getStatusCode());
            }

        }
    }

    private enum API_URL_KEYS {
        GetPlayerItems, GetSchema
    }

    private static final Logger logger = LoggerFactory.getLogger(TF2Template.class);

    private static final String DEFAULT_BASE_URL = "http://api.steampowered.com";
    private final String apiKey;
    private final Map<API_URL_KEYS, String> urls;
    private static final String apiGetPlayerItems = "/IEconItems_440/GetPlayerItems/v0001/?key={key}&steamId={steamId}";
    private static final String apiGetSchema = "/IEconItems_440/GetSchema/v0001/?key={key}&language={language}";
    private final RestTemplate restTemplate;
    private final String language;
    private final ConcurrentMap<String, TF2Schema> schemaCache;

    protected volatile long lastModified;

    public TF2Template(String apiKey) {
        this(apiKey, DEFAULT_BASE_URL, "en_US");
    }

    public TF2Template(String apiKey, String baseUrl, String language) {
        this.apiKey = apiKey;
        urls = new HashMap<API_URL_KEYS, String>();
        urls.put(API_URL_KEYS.GetPlayerItems, baseUrl + apiGetPlayerItems);
        urls.put(API_URL_KEYS.GetSchema, baseUrl + apiGetSchema);
        restTemplate = new RestTemplate();
        this.language = language;
        this.schemaCache = new ConcurrentHashMap<String, TF2Schema>(1);
    }

    public TF2Schema getSchema() {
        return getSchema(false);
    }

    public TF2Schema getSchema(final boolean forceRefresh) {

        RetryTemplate template = createRetryTemplate();
        final TF2Schema schema = template.execute(new RetryCallback<TF2Schema, RuntimeException>() {
            public TF2Schema doWithRetry(RetryContext context) {
                logWarningIfThisIsARetryAttempt(context);

                Map<String, String> urlVariables = new HashMap<String, String>(2);
                urlVariables.put("key", apiKey);
                urlVariables.put("language", language);
                ResponseExtractor<ResponseData> responseExtractor = new SchemaResponseExtractor();
                if (forceRefresh || schemaCache.get("schema") == null) {
                    // do not set any headers
                    ResponseData response = restTemplate.execute(urls.get(API_URL_KEYS.GetSchema), HttpMethod.GET, null, responseExtractor, urlVariables);
                    // set the lastmodified response and put to the cache.
                    lastModified = response.getLastModified();
                    schemaCache.put("schema", new TF2Schema(response.getData()));
                } else {
                    // set headers to only return a newer version for the given
                    // date
                    RequestCallback rcb = new SchemaRequestCallback();
                    ResponseData response = restTemplate.execute(urls.get(API_URL_KEYS.GetSchema), HttpMethod.GET, rcb, responseExtractor, urlVariables);
                    // if the schema is non null, we should replace the cached
                    // version (since there has been an update)
                    if (response != null) {
                        lastModified = response.getLastModified();
                        schemaCache.put("schema", new TF2Schema(response.getData()));
                    }
                }
                return schemaCache.get("schema");
            }

            private void logWarningIfThisIsARetryAttempt(RetryContext context) {
                if (context.getRetryCount() > 0) {
                    logger.warn("Problem fetching schema (Retry attempt:{})", context.getRetryCount());
                } else {
                    logger.debug("Calling schema api");
                }
            }
        });
        return schema;

    }

    public TF2Backpack getPlayerItems(final Long steamId) {
        RetryTemplate template = createRetryTemplate();
        final TF2Backpack backpack = template.execute(new RetryCallback<TF2Backpack, RuntimeException>() {
            public TF2Backpack doWithRetry(RetryContext context) {
                logWarningIfThisIsARetryAttempt(context);

                Map<String, String> urlVariables = new HashMap<String, String>(2);
                urlVariables.put("key", apiKey);
                urlVariables.put("steamId", steamId.toString());
                ResponseExtractor<JsonNode> responseExtractor = new ResponseExtractor<JsonNode>() {

                    @Override
                    public JsonNode extractData(ClientHttpResponse response) throws IOException {

                        if (response.getStatusCode() == HttpStatus.OK) {
                            InputStream inputStream = null;
                            try {
                                inputStream = response.getBody();
                                Reader streamReader = new InputStreamReader(inputStream, "UTF-8");
                                ObjectMapper m = new ObjectMapper();
                                JsonNode jsonBackpack = m.readTree(streamReader);
                                return jsonBackpack;
                            } finally {
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                            }
                        } else {
                            throw new RuntimeException("SteamAPI returned non-200 status code.{}" + response.getStatusCode());
                        }

                    }
                };
                JsonNode jsonBackpack = restTemplate.execute(urls.get(API_URL_KEYS.GetPlayerItems), HttpMethod.GET, null, responseExtractor, urlVariables);

                TF2Backpack backpack = new TF2Backpack(jsonBackpack, steamId);
                return backpack;
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
