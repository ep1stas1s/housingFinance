package com.kakaopay.housingfinance;

import com.kakaopay.housingfinance.institute.service.dto.FileInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractWebTestClient {
    private static final String API_INSTITUTES = "/api/institutes";

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        FileInfoDto fileInfoDto = new FileInfoDto("data.csv");
        post(API_INSTITUTES + "/init", fileInfoDto)
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    protected <T> WebTestClient.ResponseSpec get(String uri, Object... urlVariables) {
        return webTestClient.get()
                .uri(uri, urlVariables)
                .exchange();
    }

    protected <T> WebTestClient.ResponseSpec post(String uri, T dto) {
        return webTestClient.post()
                .uri(uri)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }
}
