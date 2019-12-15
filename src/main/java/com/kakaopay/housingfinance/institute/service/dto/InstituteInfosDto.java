package com.kakaopay.housingfinance.institute.service.dto;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
@NoArgsConstructor
public class InstituteInfosDto {
    private List<InstituteInfo> institutesInfos;

    public InstituteInfosDto(List<InstituteInfo> institutesInfos) {
        this.institutesInfos = institutesInfos;
    }

    public List<InstituteInfo> getInstitutesInfos() {
        return Collections.unmodifiableList(institutesInfos);
    }

    public int size() {
        return institutesInfos.size();
    }
}
