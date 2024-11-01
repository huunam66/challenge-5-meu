package com.challenge3.app.domain.profile.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDTO {

    UUID id;

    String first_name;

    String last_name;

    String identification_code;

    Date birthDay;

    String gender;

    String avatar;

    String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    ProfileLocationDTO profileLocation;
}
