package cmi.tic.com.config.webClient;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccessToken {

    @Autowired
    private WebClientConfig webClient;

    private final Logger log = LoggerFactory.getLogger(AccessToken.class);

    private static String CLIENT_SECRET = "client_secret";
    private static String CLIENT_ID = "client_id";
    private static String GRANT_TYPE = "grant_type";
    private static String SECRET = "internal";
    private static String CLIENT_CREDENTIALS = "client_credentials";

    private static String TOKEN_URL = "/protocol/openid-connect/token";

    @Value("${spring.security.oauth2.client.provider.oidc.issuer-uri}")
    private String oauthUrl;

    public String getToken() {
        try {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add(CLIENT_ID, SECRET);
            formData.add(CLIENT_SECRET, SECRET);
            formData.add(GRANT_TYPE, CLIENT_CREDENTIALS);
            WebClient.ResponseSpec result = webClient
                .getWebClient()
                .post()
                .uri(oauthUrl.concat(TOKEN_URL))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve();

            String accessToken[] = { "" };
            result
                .bodyToMono(HashMap.class)
                .subscribe(service -> {
                    log.info("Subscribe Result to get Access token with wait time 1000 ms, <access_token>");
                    accessToken[0] = (service.get("access_token").toString());
                });
            Thread.sleep(1000);
            // If this log executes first then the above one, then increase the thread sleep value.
            log.info("Returning the access token fetched from keycloak Server!");
            return accessToken[0];
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }
}
