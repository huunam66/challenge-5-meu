package com.challenge3.app.domain.location.province.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDTO {

    private String id;

    private String name;

    private String name_en;

    private String full_name;

    private String full_name_en;

    private String code_name;
}
