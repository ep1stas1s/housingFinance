package com.kakaopay.housingfinance.utils.csvData.exception;

public class FileNotFoundException extends RuntimeException {
    private static final String MESSAGE = "파일을 찾을 수 없습니다. 파일 이름 : %s";

    public FileNotFoundException(String fileName) {
        super(String.format(MESSAGE, fileName));
    }
}
