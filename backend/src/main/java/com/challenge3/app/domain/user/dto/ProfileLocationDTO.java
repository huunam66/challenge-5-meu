package com.challenge3.app.domain.user.dto;

import com.challenge3.app.domain.location.district.dto.DistrictDTO;
import com.challenge3.app.domain.location.province.dto.ProvinceDTO;
import com.challenge3.app.domain.location.ward.dto.WardDTO;
import com.challenge3.app.domain.location.ward.dto.WardDistrictProvinceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileLocationDTO {

    private UUID id;

    private String home_number;

    private String street;

    private String country;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private WardDistrictProvinceDTO ward;
}
