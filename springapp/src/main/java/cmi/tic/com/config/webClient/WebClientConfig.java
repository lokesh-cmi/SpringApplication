package cmi.tic.com.config.webClient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public WebClient getWebClient() {
        // way to retrieve  the access_token from SecurityContextHolder
        //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //        System.out.println("Authentication: " +authentication);
        //        String token = null;
        //        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)
        //            token = jwtAuthenticationToken.getToken().getTokenValue();

        return WebClient
            .builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString())
            .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder serviceWebClient() {
        return WebClient.builder();
    }
}
