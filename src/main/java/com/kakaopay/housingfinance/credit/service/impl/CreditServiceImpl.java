package com.kakaopay.housingfinance.credit.service.impl;

import com.kakaopay.housingfinance.credit.domain.MonthlyCredit;
import com.kakaopay.housingfinance.credit.domain.repository.MonthlyCreditRepository;
import com.kakaopay.housingfinance.credit.service.CreditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CreditServiceImpl implements CreditService {
    private final MonthlyCreditRepository monthlyCreditRepository;

    public CreditServiceImpl(MonthlyCreditRepository monthlyCreditRepository) {
        this.monthlyCreditRepository = monthlyCreditRepository;
    }

    /**
     * 월별 신용보증 금액을 저장
     *
     * @param monthlyCredits 월별 신용보증 금액 list
     * @return saved monthlyCredits list
     */
    @Override
    public List<MonthlyCredit> saveAll(List<MonthlyCredit> monthlyCredits) {
        return monthlyCreditRepository.saveAll(monthlyCredits);
    }
}
