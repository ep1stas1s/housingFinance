package com.kakaopay.housingfinance.credit.controller;

import com.kakaopay.housingfinance.credit.service.CreditService;
import com.kakaopay.housingfinance.credit.service.dto.MinMaxCreditDto;
import com.kakaopay.housingfinance.credit.service.dto.TopAmountByYearDto;
import com.kakaopay.housingfinance.credit.service.dto.YearlyCreditStatisticsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credits")
public class CreditApiController {
    private final CreditService creditService;

    public CreditApiController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping("/total")
    public ResponseEntity<YearlyCreditStatisticsDto> detailListByYear() {
        return ResponseEntity.ok(creditService.findAllYearlyDetailCredit());
    }

    @GetMapping("/top")
    public ResponseEntity<TopAmountByYearDto> topAmount() {
        return ResponseEntity.ok(creditService.findTopAmountInstituteByYear());
    }

    @GetMapping("/{instituteId}/avg-min-max")
    public ResponseEntity<MinMaxCreditDto> averageMinAndMax(@PathVariable long instituteId) {
        return ResponseEntity.ok(creditService.findAverageMinNadMax(instituteId));
    }
}
