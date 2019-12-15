package com.kakaopay.housingfinance;

import com.kakaopay.housingfinance.institute.service.dto.FileInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public abstract class AbstractWebTestClient {
    private static final String API_INSTITUTES = "/api/institutes";

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(documentationConfiguration(restDocumentation))
                .build();
        FileInfoDto fileInfoDto = new FileInfoDto("data.csv");

        post(API_INSTITUTES + "/init", fileInfoDto)
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(document("institutes/post-init",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .returnResult()
                .getResponseBody();
    }

    protected <T> WebTestClient.ResponseSpec get(String uri, Object... urlVariables) {
        return webTestClient.get()
                .uri(uri, urlVariables)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange();
    }

    protected <T> WebTestClient.ResponseSpec post(String uri, T dto) {
        return webTestClient.post()
                .uri(uri)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }
}
