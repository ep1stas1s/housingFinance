package com.kakaopay.housingfinance.credit.service.impl;

import com.kakaopay.housingfinance.AbstractInitializedService;
import com.kakaopay.housingfinance.credit.service.CreditService;
import com.kakaopay.housingfinance.credit.service.dto.YearlyCreditStatisticsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreditServiceImplTest extends AbstractInitializedService {

    @Autowired
    private CreditService creditService;

    @Test
    void findAllYearlyDetailCredit() {
        YearlyCreditStatisticsDto allDetailCreditByYear = creditService.findAllYearlyDetailCredit();
        assertThat(allDetailCreditByYear.size() > 1).isTrue();
    }
}