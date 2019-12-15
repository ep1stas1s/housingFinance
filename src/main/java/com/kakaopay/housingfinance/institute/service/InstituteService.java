package com.kakaopay.housingfinance.institute.service;

import com.kakaopay.housingfinance.institute.service.dto.FileInfoDto;
import com.kakaopay.housingfinance.institute.service.dto.InstituteInfosDto;

public interface InstituteService {
    int YEAR_INDEX = 0;
    int MONTH_INDEX = 1;
    int DATA_START_INDEX = 2;
    String PREFIX_CLASSPATH = "classpath:%s";
    String PREFIX_BNK = "bnk%04d";
    int INDEX_CORRECTION = 1;

    void initData(FileInfoDto fileInfoDto);

    InstituteInfosDto findAllInfosDto();
}
