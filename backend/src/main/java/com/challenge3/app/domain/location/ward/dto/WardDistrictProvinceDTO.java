package com.challenge3.app.domain.location.ward.dto;

import com.challenge3.app.domain.location.district.dto.DistrictProvinceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WardDistrictProvinceDTO extends WardDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DistrictProvinceDTO district;
}
