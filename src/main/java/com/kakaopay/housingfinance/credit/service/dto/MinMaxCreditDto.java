package com.kakaopay.housingfinance.credit.service.dto;

import com.kakaopay.housingfinance.credit.domain.YearlyAverageCredit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class MinMaxCreditDto {
    private String instituteName;
    private YearlyCreditDto minAverage;
    private YearlyCreditDto maxAverage;

    public MinMaxCreditDto(String instituteName, YearlyCreditDto minAverage, YearlyCreditDto maxAverage) {
        this.instituteName = instituteName;
        this.minAverage = minAverage;
        this.maxAverage = maxAverage;
    }

    public static MinMaxCreditDto from(List<YearlyAverageCredit> allYearlyAverageCredit) {
        YearlyAverageCredit minAverageCredit = allYearlyAverageCredit.get(0);
        YearlyCreditDto minDto = YearlyCreditDto.from(minAverageCredit);
        YearlyCreditDto maxDto = YearlyCreditDto.from(allYearlyAverageCredit.get(allYearlyAverageCredit.size() - 1));

        return new MinMaxCreditDto(minAverageCredit.getInstituteName(), minDto, maxDto);
    }
}
