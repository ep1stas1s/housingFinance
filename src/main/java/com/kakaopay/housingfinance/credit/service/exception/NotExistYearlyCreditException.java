package com.kakaopay.housingfinance.credit.service.exception;

public class NotExistYearlyCreditException extends RuntimeException {
    private static final String MESSAGE = "조회된 연간 지원금액이 없습니다.";

    public NotExistYearlyCreditException() {
        super(MESSAGE);
    }
}
