package com.challenge3.app.domain.location.district.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDTO {

    private String id;

    private String name;

    private String name_en;

    private String full_name;

    private String full_name_en;

    private String code_name;

}
