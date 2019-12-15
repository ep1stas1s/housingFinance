package com.kakaopay.housingfinance.utils.csvData.impl;

import com.kakaopay.housingfinance.utils.StreamUtils;
import com.kakaopay.housingfinance.utils.csvData.CsvData;
import com.kakaopay.housingfinance.utils.csvData.exception.CsvDataException;
import com.kakaopay.housingfinance.utils.csvData.exception.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class IntegerRowCsvData implements CsvData<Integer> {
    private static final String COMMA = ",";
    private static final String BLANK = "";
    private static final int FIRST_LINE = 0;
    private static final int SKIP_COUNT = 1;
    private static final String CURRENCY_REGEX = "\\(억원\\)";

    private final List<String> headers;
    private final List<Integer[]> rows;

    public IntegerRowCsvData(Resource resource) {
        List<CSVRecord> records = readCsvData(getPath(resource));
        this.headers = extractHeader(records.get(FIRST_LINE));
        this.rows = getRow(records);
    }

    private URL getPath(Resource resource) {
        try {
            return resource.getURL();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileNotFoundException(resource.getFilename());
        }
    }

    @Override
    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public List<Integer[]> getRows() {
        return Collections.unmodifiableList(rows);
    }

    public Stream<Integer[]> streamRows() {
        return rows.stream();
    }

    private List<CSVRecord> readCsvData(URL path) {
        try (CSVParser parse = CSVParser.parse(path, StandardCharsets.UTF_8, CSVFormat.DEFAULT)) {
            return parse.getRecords();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new CsvDataException();
        }
    }

    private List<String> extractHeader(CSVRecord record) {
        return StreamUtils.stream(record.iterator())
                .filter(this::hasContents)
                .map(this::excludeCurrency)
                .map(String::trim)
                .collect(toList());
    }

    private String excludeCurrency(String column) {
        return column.replaceAll(CURRENCY_REGEX, BLANK);
    }

    private List<Integer[]> getRow(List<CSVRecord> records) {
        return records.stream()
                .skip(SKIP_COUNT)
                .map(this::readRow)
                .collect(toList());
    }

    private Integer[] readRow(CSVRecord record) {
        return StreamUtils.stream(record.iterator())
                .filter(this::hasContents)
                .map(String::trim)
                .map(this::parsePriceToInt)
                .toArray(Integer[]::new);
    }

    private boolean hasContents(String str) {
        return !str.isEmpty();
    }

    private int parsePriceToInt(String price) {
        return Integer.parseInt(price.replaceAll(COMMA, BLANK).trim());
    }
}
