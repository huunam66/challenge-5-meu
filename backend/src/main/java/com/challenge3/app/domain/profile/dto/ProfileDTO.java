package com.challenge3.app.domain.profile.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProfileDTO {

    private UUID id;

    private String first_name;

    private String last_name;

    private String identification_code;

    private Date birthDay;

    private String gender;

    private String avatar;

    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfileLocationDTO profileLocation;
}
