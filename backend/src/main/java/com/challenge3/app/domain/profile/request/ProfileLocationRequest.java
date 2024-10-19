package com.challenge3.app.domain.profile.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileLocationRequest {

    @Schema(example = "123A", description = "Số nhà")
    String home_number;

    @Schema(example = "Đường Sông Thao", description = "Tên đường")
    String street;

    @Schema(example = "Việt Nam", description = "Tên quốc gia")
    String country;

    @Schema(example = "id-1", description = "Mã phường/xã")
    String wardId;
}
