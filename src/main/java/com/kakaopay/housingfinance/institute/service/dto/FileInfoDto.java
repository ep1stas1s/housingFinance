package com.kakaopay.housingfinance.institute.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class FileInfoDto {
    private String fileName;

    public FileInfoDto(String fileName) {
        this.fileName = fileName;
    }
}
