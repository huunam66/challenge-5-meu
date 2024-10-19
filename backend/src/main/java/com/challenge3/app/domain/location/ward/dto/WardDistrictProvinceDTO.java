package com.challenge3.app.domain.location.ward.dto;

import com.challenge3.app.domain.location.district.dto.DistrictProvinceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class WardDistrictProvinceDTO extends WardDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    DistrictProvinceDTO district;
}
