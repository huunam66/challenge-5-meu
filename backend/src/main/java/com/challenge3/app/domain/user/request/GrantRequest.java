package com.challenge3.app.domain.user.request;

import com.challenge3.app.configuration.auth.role.ROLE;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrantRequest {

    @Schema(example = "nguyenvana123@gmail.com", description = "Địa chỉ email")
    @NotBlank(message = "Email là bắt buộc!")
    @Email(message = "Email không hợp lệ!")
    @Size(min = 10, message = "Email chứa ít nhất 10 kí tự")
    @Size(max = 100, message = "Email chứa nhiều nhất 100 kí tự")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ!")
    private String email;

    @Schema(example = "USER (ghi hoa)", description = "Vai trò")
    @NotNull(message = "Tên vai trò là bắt buộc!")
    @Enumerated(EnumType.STRING)
    private ROLE role;

}
