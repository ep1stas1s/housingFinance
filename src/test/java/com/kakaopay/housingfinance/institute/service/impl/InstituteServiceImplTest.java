package com.kakaopay.housingfinance.institute.service.impl;

import com.kakaopay.housingfinance.AbstractInitializedService;
import com.kakaopay.housingfinance.institute.service.dto.InstituteInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstituteServiceImplTest extends AbstractInitializedService {

    @Test
    void findAllInfosDto() {
        assertThat(instituteService.findAllInfosDto().getInstitutesInfos())
                .contains(new InstituteInfo("주택도시기금1", "bnk0001"))
                .contains(new InstituteInfo("국민은행", "bnk0002"))
                .contains(new InstituteInfo("외환은행", "bnk0008"));
    }
}