package com.challenge3.app.domain.user.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "Email là bắt buộc!")
    @Email(message = "Email không hợp lệ!")
    @Size(min = 10, message = "Email chứa ít nhất 10 kí tự")
    @Size(max = 100, message = "Email chứa nhiều nhất 100 kí tự")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ!")
    private String email;

    @NotBlank(message = "Mật khẩu là bắt buộc!")
    @NotNull(message = "Mật khẩu là bắt buộc!")
    @Size(min = 10, message = "Mât khẩu chứa ít nhất 10 kí tự")
    @Size(max = 30, message = "Mât khẩu chứa nhiều nhất 30 kí tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Cho phép ít nhất một kí tự hoa, kí tự thường và số từ 0 -> 9")
    private String password;
}
