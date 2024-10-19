package com.challenge3.app.domain.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserRequest {

    @NotBlank(message = "Email là bắt buộc!")
    @Email(message = "Email không hợp lệ!")
    @Size(min = 10, message = "Email chứa ít nhất 10 kí tự")
    @Size(max = 100, message = "Email chứa nhiều nhất 100 kí tự")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ!")
    @Schema(description = "-> Email của người dùng", example = "email123@gmail.com")
    private String email;

    @NotBlank(message = "Mật khẩu là bắt buộc!")
    @NotNull(message = "Mật khẩu là bắt buộc!")
    @Size(min = 10, message = "Mât khẩu chứa ít nhất 10 kí tự")
    @Size(max = 30, message = "Mât khẩu chứa nhiều nhất 30 kí tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9])(?!.*[^a-zA-Z0-9]{2,}).{8,}$", message = "Cho phép ít nhất một kí tự hoa, kí tự thường và số từ 0 -> 9 và đối đa 2 kí tự đặc biệt!")
    @Schema(description = "-> Mật khẩu của người dùng", example = "pass.word@123")
    private String password;

}
