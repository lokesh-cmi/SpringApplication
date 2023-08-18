package cmi.tic.com.web.rest.comm;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ServerResourceUT {

    private ServerResource serverResource;

    @BeforeEach
    void init() {
        serverResource = new ServerResource();
    }

    @Test
    void getServer() {
        Mono<ResponseEntity<Map<String, String>>> response = serverResource.getServer();
        Map<String, String> result = response.block().getBody();
        Assertions.assertEquals(result.get("server"), "This is Server springapp");
    }
}
