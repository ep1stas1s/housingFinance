package com.kakaopay.housingfinance.utils.csvData;

import java.util.List;

public interface CsvData<T> {
    List<String> getHeaders();

    List<T[]> getRows();
}
