package com.kakaopay.housingfinance.credit.controller;

import com.kakaopay.housingfinance.AbstractWebTestClient;
import com.kakaopay.housingfinance.credit.service.dto.YearlyCreditStatisticsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreditApiControllerTest extends AbstractWebTestClient {

    private static final String API_CREDIT = "/api/credit";

    @Test
    @DisplayName("연도별 금융기관의 지원금액 합계를 반환")
    void get_yearlyCreditStatistics() {
        YearlyCreditStatisticsDto yearlyCreditStatisticsDto = get(API_CREDIT + "/total")
                .expectStatus().isOk()
                .expectBody(YearlyCreditStatisticsDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(yearlyCreditStatisticsDto.size()).isEqualTo(13);
    }
}
