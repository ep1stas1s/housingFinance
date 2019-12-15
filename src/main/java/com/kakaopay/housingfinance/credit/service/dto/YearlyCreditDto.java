package com.kakaopay.housingfinance.credit.service.dto;

import com.kakaopay.housingfinance.credit.domain.YearlyAverageCredit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class YearlyCreditDto {
    private int year;
    private int amount;

    public YearlyCreditDto(int year, int amount) {
        this.year = year;
        this.amount = amount;
    }

    public static YearlyCreditDto from(YearlyAverageCredit yearlyAverageCredit) {
        return new YearlyCreditDto(
                yearlyAverageCredit.getYear(),
                (int) Math.round(yearlyAverageCredit.getAverageAmount())
        );
    }
}
