package com.kakaopay.housingfinance.institute.controller;

import com.kakaopay.housingfinance.institute.service.InstituteService;
import com.kakaopay.housingfinance.institute.service.dto.FileInfoDto;
import com.kakaopay.housingfinance.institute.service.dto.InstituteInfosDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/institutes")
public class InstituteApiController {

    private final InstituteService instituteService;

    public InstituteApiController(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    @PostMapping("/init")
    public ResponseEntity<?> create(@RequestBody FileInfoDto fileInfoDto) {
        instituteService.initData(fileInfoDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<InstituteInfosDto> list() {
        return ResponseEntity.ok(instituteService.findAllInfosDto());
    }
}
