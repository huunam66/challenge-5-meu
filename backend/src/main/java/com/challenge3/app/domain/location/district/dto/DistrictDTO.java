package com.challenge3.app.domain.location.district.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class DistrictDTO {

    String id;

    String name;

    String name_en;

    String full_name;

    String full_name_en;

    String code_name;

}
