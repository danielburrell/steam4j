package uk.co.solong.steam4j.auth;

import static uk.co.solong.steam4j.auth.SteamAuth.OpenIdKeys.CLAIMED_ID;
import static uk.co.solong.steam4j.auth.SteamAuth.OpenIdKeys.IDENTITY;
import static uk.co.solong.steam4j.auth.SteamAuth.OpenIdKeys.MODE;
import static uk.co.solong.steam4j.auth.SteamAuth.OpenIdKeys.NS;
import static uk.co.solong.steam4j.auth.SteamAuth.OpenIdKeys.REALM;
import static uk.co.solong.steam4j.auth.SteamAuth.OpenIdKeys.RETURN_TO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class SteamAuth {
    private static final Logger logger = LoggerFactory.getLogger(SteamAuth.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String loginUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public AuthorizationResult authenticate(HttpServletRequest r) {
        if (!"id_res".equals(r.getParameter(OpenIdKeys.MODE.getParam()))) {
            throw new RuntimeException("AuthSequenceFail");
        }
        Map<String, String[]> incomingParamters = r.getParameterMap();
        Map<String, String> params = new HashMap<String, String>();
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl("https://steamcommunity.com/openid/login");
        for (OpenIdKeys openIdKey : OpenIdKeys.values()) {
            if (!openIdKey.equals(OpenIdKeys.MODE)) {
                if (openIdKey.isResponse()) {
                    String openIdKeyAsString = openIdKey.getParam();
                    String[] parameter = incomingParamters.get(openIdKeyAsString);
                    if (parameter != null) {
                        urlBuilder.queryParam(openIdKeyAsString, "{" + openIdKeyAsString + "}");
                        params.put(openIdKeyAsString, parameter[0]);
                    }
                }
            } else {
                urlBuilder.queryParam(OpenIdKeys.MODE.getParam(), "check_authentication");
            }
        }
        String url = urlBuilder.build().toUriString();
        String result = restTemplate.getForObject(url, String.class, params);
        if (result != null && result.contains("is_valid:true")) {
            AuthorizationResult a = new AuthorizationResult(extractSteamIdFromClaim(incomingParamters.get(OpenIdKeys.CLAIMED_ID.getParam())), true);
            return a;
        } else {
            AuthorizationResult a = new AuthorizationResult(null, false);
            return a;
        }
    }

    private String extractSteamIdFromClaim(String[] param) {
        String[] results = param[0].split("/");
        return results[results.length-1];
    }

    public SteamAuth(String realm, String returnTo) throws UnsupportedEncodingException {

        loginUrl = UriComponentsBuilder.fromHttpUrl("https://steamcommunity.com/openid/login")
                .queryParam(NS.getParam(), URLEncoder.encode("http://specs.openid.net/auth/2.0", "UTF-8")).queryParam(MODE.getParam(), "checkid_setup")
                .queryParam(RETURN_TO.getParam(), URLEncoder.encode(returnTo, "UTF-8")).queryParam(REALM.getParam(), URLEncoder.encode(realm, "UTF-8"))
            //    .queryParam("openid.ns.sreg", URLEncoder.encode("http://openid.net/extensions/sreg/1.1", "UTF-8"))
                .queryParam(CLAIMED_ID.getParam(), URLEncoder.encode("http://specs.openid.net/auth/2.0/identifier_select", "UTF-8"))
                .queryParam(IDENTITY.getParam(), URLEncoder.encode("http://specs.openid.net/auth/2.0/identifier_select", "UTF-8")).build().toUriString();

    }

    enum OpenIdKeys {
        NS("openid.ns", true, true), OP_ENDPOINT("openid.op_endpoint", true, false), CLAIMED_ID("openid.claimed_id", true, true), IDENTITY("openid.identity",
                true, true), RETURN_TO("openid.return_to", true, true), RESPONSE_NONCE("openid.response_nonce", true, false), ASSOC_HANDLE(
                "openid.assoc_handle", true, false), SIGNED("openid.signed", true, false), SIG("openid.sig", true, false), MODE("openid.mode", true, true), REALM(
                "openid.realm", false, true);
        private String param;
        private boolean response;
        private boolean request;

        private OpenIdKeys(String param, boolean response, boolean request) {
            this.param = param;
            this.response = response;
            this.request = request;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public boolean isResponse() {
            return response;
        }

        public void setResponse(boolean response) {
            this.response = response;
        }

        public boolean isRequest() {
            return request;
        }

        public void setRequest(boolean request) {
            this.request = request;
        }
    }
}
