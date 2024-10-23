package com.challenge3.app.domain.profile.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadAvatarDTO {

    String avatar;
    boolean uploaded;
}
