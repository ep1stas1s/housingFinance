package com.kakaopay.housingfinance.credit.service.impl;

import com.kakaopay.housingfinance.credit.domain.MonthlyCredit;
import com.kakaopay.housingfinance.credit.domain.YearlyAverageCredit;
import com.kakaopay.housingfinance.credit.domain.YearlyCredit;
import com.kakaopay.housingfinance.credit.domain.repository.MonthlyCreditRepository;
import com.kakaopay.housingfinance.credit.service.CreditService;
import com.kakaopay.housingfinance.credit.service.dto.*;
import com.kakaopay.housingfinance.credit.service.exception.NotExistYearlyCreditException;
import com.kakaopay.housingfinance.credit.service.exception.NotFoundAverageCreditException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
public class CreditServiceImpl implements CreditService {
    private static final int NONE = 0;

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
    @Transactional
    public List<MonthlyCredit> saveAll(List<MonthlyCredit> monthlyCredits) {
        return monthlyCreditRepository.saveAll(monthlyCredits);
    }

    /**
     * 연도별 기관 데이터를 조회하여 통계(totalAmount, detailAmount)를 낸다
     *
     * @return totalAmount, detailAmount, List<instituteCredit>를 포함한 DTO
     */
    @Override
    public YearlyCreditStatisticsDto findAllYearlyDetailCredit() {
        List<YearlyCredit> yearlyCreditInfos = findAllYearlyCreditInfo();
        List<DetailYearlyCreditInfoDto> groupedYearlyCreditInfo = groupingDetailYearlyCreditInfo(yearlyCreditInfos);
        return new YearlyCreditStatisticsDto(groupedYearlyCreditInfo);
    }

    private List<YearlyCredit> findAllYearlyCreditInfo() {
        return monthlyCreditRepository.findAllYearlyCreditInfo();
    }

    private List<DetailYearlyCreditInfoDto> groupingDetailYearlyCreditInfo(List<YearlyCredit> yearlyCreditInfos) {
        Map<Integer, Integer> totalAmountByYear = sumTotalAmountByYear(yearlyCreditInfos);
        Map<Integer, List<YearlyCredit>> instituteCreditByYear = groupingInstituteByYear(yearlyCreditInfos);

        return totalAmountByYear.keySet().stream()
                .map(year -> new DetailYearlyCreditInfoDto(
                        year,
                        totalAmountByYear.get(year),
                        mappingInstituteCredits(instituteCreditByYear.get(year)))
                ).sorted(Comparator.comparing(DetailYearlyCreditInfoDto::getYear))
                .collect(toList());
    }

    private Map<Integer, Integer> sumTotalAmountByYear(List<YearlyCredit> allYearlyCreditInfo) {
        return allYearlyCreditInfo.stream()
                .collect(groupingBy(YearlyCredit::getYear, summingInt(YearlyCredit::getDetailAmount)));
    }

    private Map<Integer, List<YearlyCredit>> groupingInstituteByYear(List<YearlyCredit> allYearlyCreditInfo) {
        return allYearlyCreditInfo.stream()
                .collect(groupingBy(YearlyCredit::getYear, toList()));
    }

    private List<InstituteCreditDto> mappingInstituteCredits(List<YearlyCredit> yearlyCredits) {
        return yearlyCredits.stream()
                .map(yearlyCredit -> new InstituteCreditDto(yearlyCredit.getInstitute().getName(), yearlyCredit.getDetailAmount()))
                .collect(toList());
    }

    @Override
    public TopAmountByYearDto findTopAmountInstituteByYear() {
        YearlyCredit yearlyCredit = findAllYearlyCreditInfo().stream()
                .max(Comparator.comparing(YearlyCredit::getDetailAmount))
                .orElseThrow(NotExistYearlyCreditException::new);

        return TopAmountByYearDto.from(yearlyCredit);
    }

    @Override
    public MinMaxCreditDto findAverageMinNadMax(long instituteId) {
        List<YearlyAverageCredit> allYearlyAverageCredit = findAllYearlyCredit(instituteId);
        if (isEmptyRow(allYearlyAverageCredit)) {
            log.info("instituteId : {}, 해당 기관의 정보가 존재하지 않습니다.", instituteId);
            throw new NotFoundAverageCreditException();
        }

        return MinMaxCreditDto.from(allYearlyAverageCredit);
    }

    private List<YearlyAverageCredit> findAllYearlyCredit(long instituteId) {
        return monthlyCreditRepository.findAllYearlyCredit(instituteId);
    }

    private boolean isEmptyRow(List<YearlyAverageCredit> allYearlyCredit) {
        return allYearlyCredit.size() == NONE;
    }
}
