package com.kakaopay.housingfinance.utils.csvData.exception;

public class CsvDataException extends RuntimeException {
    private static final String MESSAGE = "Fail to Read Data!!";

    public CsvDataException() {
        super(MESSAGE);
    }
}
