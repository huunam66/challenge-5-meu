package com.challenge3.app.domain.location.province.dto;
import com.challenge3.app.domain.location.district.dto.DistrictDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDistrictsDTO extends ProvinceDTO {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<DistrictDTO> districts;

}

