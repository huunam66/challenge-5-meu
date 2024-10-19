package com.challenge3.app.domain.profile.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequest {

    @Schema(example = "Nguyễn", description = "Họ")
    String first_name;

    @Schema(example = "Văn A", description = "Tên đệm, tên")
    String last_name;

    @Schema(example = "12334656343", description = "Số căn cước")
    String identification_code;

    @Schema(description = "Ngày sinh")
    Date birthDay;

    @Schema(example = "Nam", description = "Giới tính")
    String gender;

    @Schema(example = "https://www.link.com/avatar.png", description = "Đường dẫn hình ảnh")
    String avatar;

    @Schema(example = "nguyenvana123@gmail.com", description = "Địa chỉ email")
    String email;
}
