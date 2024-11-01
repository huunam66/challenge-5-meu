package com.challenge3.app.domain.profile.dto;

import com.challenge3.app.domain.location.ward.dto.WardDistrictProvinceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileLocationDTO  {

    UUID id;

    String home_number;

    String street;

    String country;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    WardDistrictProvinceDTO ward;
}
