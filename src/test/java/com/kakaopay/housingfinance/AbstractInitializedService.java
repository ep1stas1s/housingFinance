package com.kakaopay.housingfinance;

import com.kakaopay.housingfinance.institute.service.InstituteService;
import com.kakaopay.housingfinance.institute.service.dto.FileInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractInitializedService {

    @Autowired
    protected InstituteService instituteService;

    @BeforeEach
    void setUp() {
        instituteService.initData(new FileInfoDto("data.csv"));
    }
}
