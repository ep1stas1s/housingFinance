package com.kakaopay.housingfinance.utils.csvData.impl;

import com.kakaopay.housingfinance.utils.csvData.CsvData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegerRowCsvDataTest {

    @Autowired
    private ResourceLoader resourceLoader;

    private CsvData<Integer> csvData;

    @BeforeEach
    void setUp() {
        csvData = new IntegerRowCsvData(resourceLoader.getResource("classpath:data.csv"));
    }

    @Test
    @DisplayName("CSV 파일로부터 header 추출")
    void getHeaders() {
        assertThat(csvData.getHeaders().get(0)).isEqualTo("연도");
        assertThat(csvData.getHeaders().get(2)).isEqualTo("주택도시기금1");
        assertThat(csvData.getHeaders().get(10)).isEqualTo("기타은행");
    }

    @Test
    @DisplayName("CSV 파일로부터 row 를 Integer 형식으로 추출")
    void getRows() {
        assertThat(csvData.getRows().get(0)[0]).isEqualTo(2005);
        assertThat(csvData.getRows().get(0)[4]).isEqualTo(82);
        assertThat(csvData.getRows().get(0)[8]).isEqualTo(57);
    }
}