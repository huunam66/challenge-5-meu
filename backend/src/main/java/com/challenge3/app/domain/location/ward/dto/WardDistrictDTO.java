package com.challenge3.app.domain.location.ward.dto;

import com.challenge3.app.domain.location.district.dto.DistrictDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WardDistrictDTO extends WardDTO {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DistrictDTO district;
}
