package com.kakaopay.housingfinance.credit.service;

import com.kakaopay.housingfinance.credit.domain.MonthlyCredit;
import com.kakaopay.housingfinance.credit.service.dto.YearlyCreditStatisticsDto;

import java.util.List;

public interface CreditService {
    List<MonthlyCredit> saveAll(List<MonthlyCredit> monthlyCredits);

    YearlyCreditStatisticsDto findAllYearlyDetailCredit();
}
