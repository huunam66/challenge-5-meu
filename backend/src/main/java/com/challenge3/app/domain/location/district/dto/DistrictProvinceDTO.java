package com.challenge3.app.domain.location.district.dto;


import com.challenge3.app.domain.location.province.dto.ProvinceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DistrictProvinceDTO extends DistrictDTO{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    ProvinceDTO province;
}
