package com.kakaopay.housingfinance.credit.service.dto;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
@NoArgsConstructor
public class DetailYearlyCreditInfoDto {
    private int year;
    private int totalAmount;
    private List<InstituteCreditDto> detailAmount;

    public DetailYearlyCreditInfoDto(int year, int totalAmount, List<InstituteCreditDto> detailAmount) {
        this.year = year;
        this.totalAmount = totalAmount;
        this.detailAmount = detailAmount;
    }

    public int getYear() {
        return year;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public List<InstituteCreditDto> getDetailAmount() {
        return Collections.unmodifiableList(detailAmount);
    }
}
