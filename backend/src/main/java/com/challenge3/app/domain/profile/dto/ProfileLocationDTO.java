package com.challenge3.app.domain.profile.dto;

import com.challenge3.app.domain.location.ward.dto.WardDistrictProvinceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileLocationDTO  {

    private UUID id;

    private String home_number;

    private String street;

    private String country;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private WardDistrictProvinceDTO ward;
}
