package com.kakaopay.housingfinance.credit.domain;

public interface YearlyAverageCredit {
    Integer getYear();

    String getInstituteName();

    Double getAverageAmount();
}
