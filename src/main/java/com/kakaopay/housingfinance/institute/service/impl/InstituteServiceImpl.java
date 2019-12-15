package com.kakaopay.housingfinance.institute.service.impl;

import com.kakaopay.housingfinance.credit.domain.MonthlyCredit;
import com.kakaopay.housingfinance.institute.domain.Institute;
import com.kakaopay.housingfinance.institute.domain.repository.InstituteRepository;
import com.kakaopay.housingfinance.institute.service.InstituteService;
import com.kakaopay.housingfinance.institute.service.dto.FileInfoDto;
import com.kakaopay.housingfinance.institute.service.dto.InstituteInfo;
import com.kakaopay.housingfinance.institute.service.dto.InstituteInfosDto;
import com.kakaopay.housingfinance.utils.csvData.impl.IntegerRowCsvData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;


@Slf4j
@Service
public class InstituteServiceImpl implements InstituteService {
    private static final int NONE = 0;

    private final InstituteRepository instituteRepository;
    private final ResourceLoader resourceLoader;

    public InstituteServiceImpl(InstituteRepository instituteRepository,
                                ResourceLoader resourceLoader) {
        this.instituteRepository = instituteRepository;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Initialize Data in local file
     *
     * @param fileInfoDto file name
     */
    public void initData(FileInfoDto fileInfoDto) {
        if (hasContents()) {
            return;
        }
        Resource resource = resourceLoader.getResource(addPrefixClasspath(fileInfoDto));
        IntegerRowCsvData integerRowCsvData = new IntegerRowCsvData(resource);

        List<Institute> savedInstitutes = instituteRepository.saveAll(initInstitutes(integerRowCsvData.getHeaders()));
    }

    private boolean hasContents() {
        return findAll().size() > NONE;
    }

    private String addPrefixClasspath(FileInfoDto fileName) {
        return String.format(PREFIX_CLASSPATH, fileName.getFileName());
    }

    private List<Institute> initInstitutes(List<String> instituteName) {
        return IntStream.range(DATA_START_INDEX, instituteName.size())
                .mapToObj(idx -> new Institute(instituteName.get(idx), String.format(PREFIX_BNK, idx - INDEX_CORRECTION)))
                .collect(toList());
    }

    private List<MonthlyCredit> convertCsvToMonthlyCredit(List<Institute> savedInstitutes, IntegerRowCsvData integerRowCsvData) {
        Map<String, Institute> instituteCodeByName = savedInstitutes.stream()
                .collect(toMap(Institute::getName, identity()));

        return integerRowCsvData.streamRows()
                .map(row -> readRowData(row, integerRowCsvData.getHeaders(), instituteCodeByName))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private List<MonthlyCredit> readRowData(Integer[] rowData,
                                            List<String> instituteOrder,
                                            Map<String, Institute> instituteCodeByName) {
        int year = rowData[YEAR_INDEX];
        int month = rowData[MONTH_INDEX];

        List<MonthlyCredit> monthlyCredits = new ArrayList<>();
        for (int i = DATA_START_INDEX; i < instituteOrder.size(); i++) {
            MonthlyCredit monthlyCredit = MonthlyCredit.builder()
                    .year(year)
                    .month(month)
                    .supportAmount(rowData[i])
                    .institute(instituteCodeByName.get(instituteOrder.get(i)))
                    .build();

            monthlyCredits.add(monthlyCredit);
        }
        return monthlyCredits;
    }

    /**
     * All institute list
     *
     * @return InstituteInfosDto
     */
    public InstituteInfosDto findAllInfosDto() {
        List<InstituteInfo> instituteInfos = findAll().stream()
                .map(InstituteInfo::from)
                .collect(toList());
        return new InstituteInfosDto(instituteInfos);
    }

    private List<Institute> findAll() {
        return instituteRepository.findAll();
    }
}
