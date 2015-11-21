package uk.co.solong.steam4j.auth;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SteamAuthTest {

    private static final Logger logger = LoggerFactory.getLogger(SteamAuthTest.class);

    @Test
    public void shouldGenerateCorrectLoginUrl() throws UnsupportedEncodingException {
        SteamAuth testSubject = new SteamAuth("http://localhost:8080/", "https://localhost:8080/api/returnTo");
        String actual = testSubject.getLoginUrl();
        logger.info(actual);
        assertEquals(

                "https://steamcommunity.com/openid/login?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.mode=checkid_setup&openid.return_to=https%3A%2F%2Flocalhost%3A8080%2Fapi%2FreturnTo&openid.realm=http%3A%2F%2Flocalhost%3A8080%2F&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select",
                actual);
    }

}
