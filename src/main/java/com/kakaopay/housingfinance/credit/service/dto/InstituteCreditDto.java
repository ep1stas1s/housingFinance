package com.kakaopay.housingfinance.credit.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class InstituteCreditDto {
    private String instituteName;
    private int amount;

    public InstituteCreditDto(String instituteName, int amount) {
        this.instituteName = instituteName;
        this.amount = amount;
    }
}

