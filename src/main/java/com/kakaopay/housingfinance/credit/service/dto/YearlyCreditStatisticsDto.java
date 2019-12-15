package com.kakaopay.housingfinance.credit.service.dto;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
@NoArgsConstructor
public class YearlyCreditStatisticsDto {
    private List<DetailYearlyCreditInfoDto> detailYearlyCreditInfoDtos;

    public YearlyCreditStatisticsDto(List<DetailYearlyCreditInfoDto> detailYearlyCreditInfoDtos) {
        this.detailYearlyCreditInfoDtos = detailYearlyCreditInfoDtos;
    }

    public List<DetailYearlyCreditInfoDto> getDetailYearlyCreditInfoDtos() {
        return Collections.unmodifiableList(detailYearlyCreditInfoDtos);
    }

    public int size() {
        return detailYearlyCreditInfoDtos.size();
    }
}
