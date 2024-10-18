package com.challenge3.app.domain.location.district.dto;

import com.challenge3.app.domain.location.province.dto.ProvinceDTO;
import com.challenge3.app.domain.location.ward.dto.WardDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

    @EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictProvinceWardsDTO extends DistrictDTO{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProvinceDTO province;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<WardDTO> wards;

}
