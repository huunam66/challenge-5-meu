package com.challenge3.app.domain.user.dto;

import com.challenge3.app.configuration.auth.role.ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorityDTO implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    ROLE name;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return "ROLE_" + this.name.name();
    }
}
