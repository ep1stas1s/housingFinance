package com.kakaopay.housingfinance.credit.service.exception;

public class NotFoundAverageCreditException extends RuntimeException {
    private static final String MESSAGE = "해당 기관의 정보가 존재하지 않습니다.";

    public NotFoundAverageCreditException() {
        super(MESSAGE);
    }
}
