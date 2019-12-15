package com.kakaopay.housingfinance.institute.controller;

import com.kakaopay.housingfinance.AbstractWebTestClient;
import com.kakaopay.housingfinance.institute.service.dto.InstituteInfosDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstituteControllerTest extends AbstractWebTestClient {
    private static final String API_INSTITUTES = "/api/institutes";

    @Test
    @DisplayName("기관의 목록을 가져옴")
    void get_instituteList() {
        InstituteInfosDto instituteInfosDto = get(API_INSTITUTES)
                .expectStatus().isOk()
                .expectBody(InstituteInfosDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(instituteInfosDto.size() > 1).isTrue();
    }
}
