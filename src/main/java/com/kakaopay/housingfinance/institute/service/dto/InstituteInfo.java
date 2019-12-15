package com.kakaopay.housingfinance.institute.service.dto;

import com.kakaopay.housingfinance.institute.domain.Institute;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class InstituteInfo {
    private String instituteName;
    private String instituteCode;

    public InstituteInfo(String instituteName, String instituteCode) {
        this.instituteName = instituteName;
        this.instituteCode = instituteCode;
    }

    public static InstituteInfo from(Institute institute) {
        return new InstituteInfo(institute.getName(), institute.getCode());
    }
}
