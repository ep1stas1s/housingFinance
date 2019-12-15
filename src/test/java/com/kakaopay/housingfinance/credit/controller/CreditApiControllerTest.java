package com.kakaopay.housingfinance.credit.controller;

import com.kakaopay.housingfinance.AbstractWebTestClient;
import com.kakaopay.housingfinance.credit.service.dto.MinMaxCreditDto;
import com.kakaopay.housingfinance.credit.service.dto.TopAmountByYearDto;
import com.kakaopay.housingfinance.credit.service.dto.YearlyCreditStatisticsDto;
import com.kakaopay.housingfinance.web.advice.dto.ErrorMessage;
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

    @Test
    @DisplayName("각 연도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명과 연도를 반환")
    void get_topAmountBank() {
        TopAmountByYearDto topAmountByYearDto = get(API_CREDIT + "/top")
                .expectStatus().isOk()
                .expectBody(TopAmountByYearDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(topAmountByYearDto.getInstituteName()).isEqualTo("주택도시기금1");
        assertThat(topAmountByYearDto.getYear()).isEqualTo(2014);
    }

    @Test
    @DisplayName("입력받은 Institute id (pathVariable) 의 연평균 지원금액 중 최솟값, 최댓값을 조회")
    void get_averageMinAndMax() {
        MinMaxCreditDto minMaxCreditDto = get(API_CREDIT + "/{instituteId}/avg-min-max", 8)
                .expectStatus().isOk()
                .expectBody(MinMaxCreditDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(minMaxCreditDto.getInstituteName()).isEqualTo("외환은행");
        assertThat(minMaxCreditDto.getMinAverage().getYear()).isEqualTo(2008);
        assertThat(minMaxCreditDto.getMaxAverage().getYear()).isEqualTo(2015);
        assertThat(minMaxCreditDto.getMinAverage().getAmount()).isEqualTo(78);
        assertThat(minMaxCreditDto.getMaxAverage().getAmount()).isEqualTo(1702);

    }

    @Test
    @DisplayName("존재하지 않는 Institute id 로 조회할 경우, NOT_FOUND status code 및 error message 반환")
    void get_averageMinAndMax1() {
        ErrorMessage errorMessage = get(API_CREDIT + "/{instituteId}/avg-min-max", 999)
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorMessage.getMessage()).isEqualTo("해당 기관의 정보가 존재하지 않습니다.");
    }
}
