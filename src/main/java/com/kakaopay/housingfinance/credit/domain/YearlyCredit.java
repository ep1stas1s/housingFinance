package com.kakaopay.housingfinance.credit.domain;

import com.kakaopay.housingfinance.institute.domain.Institute;

public interface YearlyCredit {
    Integer getYear();

    Institute getInstitute();

    Integer getDetailAmount();
}
