package com.kakaopay.housingfinance.web.advice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ErrorMessage {
    private String message;
    private Object data;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
