package com.challenge3.app.domain.location.district.dto;


import com.challenge3.app.domain.location.province.dto.ProvinceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictProvinceDTO extends DistrictDTO{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProvinceDTO province;
}
