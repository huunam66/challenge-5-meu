package com.challenge3.app.domain.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticatedDTO {

    String token;
    boolean authenticated;
}
