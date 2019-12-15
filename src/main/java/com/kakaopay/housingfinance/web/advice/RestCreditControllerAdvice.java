package com.kakaopay.housingfinance.web.advice;

import com.kakaopay.housingfinance.credit.service.exception.NotExistYearlyCreditException;
import com.kakaopay.housingfinance.credit.service.exception.NotFoundAverageCreditException;
import com.kakaopay.housingfinance.web.advice.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestCreditControllerAdvice {

    @ExceptionHandler(value = {NotExistYearlyCreditException.class, NotFoundAverageCreditException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage unAuthentication(Exception e) {
        log.info(e.getMessage(), e);
        return new ErrorMessage(e.getMessage());
    }
}
