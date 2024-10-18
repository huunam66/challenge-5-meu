package com.challenge3.app.domain.user.request;

import com.challenge3.app.configuration.auth.role.ROLE;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserAuthorityRequest extends UserRequest {

    @NotNull(message = "Tên vai trò là bắt buộc!")
    @Enumerated(EnumType.STRING)
    @Schema(description = "-> Vai trò của người dùng", example = "ADMIN")
    private ROLE role;

}
