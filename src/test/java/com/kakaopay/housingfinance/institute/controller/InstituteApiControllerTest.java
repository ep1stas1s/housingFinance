package com.kakaopay.housingfinance.institute.controller;

import com.kakaopay.housingfinance.AbstractWebTestClient;
import com.kakaopay.housingfinance.institute.service.dto.InstituteInfosDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class InstituteApiControllerTest extends AbstractWebTestClient {
    private static final String API_INSTITUTES = "/api/institutes";

    @Test
    @DisplayName("기관의 목록을 가져옴")
    void get_instituteList() {
        InstituteInfosDto instituteInfosDto = get(API_INSTITUTES)
                .expectStatus().isOk()
                .expectBody(InstituteInfosDto.class)
                .consumeWith(document("institutes/get-list/",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .returnResult()
                .getResponseBody();

        assertThat(instituteInfosDto.size() > 1).isTrue();
    }
}
