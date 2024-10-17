package com.challenge3.app.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private UUID id;

    private String first_name;

    private String last_name;

    private String identification_code;

    private Date birthDay;

    private String gender;

    private String avatar;
}
