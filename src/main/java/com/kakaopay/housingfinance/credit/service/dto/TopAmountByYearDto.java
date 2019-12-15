package com.kakaopay.housingfinance.credit.service.dto;

import com.kakaopay.housingfinance.credit.domain.YearlyCredit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class TopAmountByYearDto {
    private int year;
    private String instituteName;

    public TopAmountByYearDto(int year, String instituteName) {
        this.year = year;
        this.instituteName = instituteName;
    }

    public static TopAmountByYearDto from(YearlyCredit yearlyCredit) {
        return new TopAmountByYearDto(yearlyCredit.getYear(), yearlyCredit.getInstitute().getName());
    }
}
