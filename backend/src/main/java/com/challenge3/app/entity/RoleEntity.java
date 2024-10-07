package com.challenge3.app.entity;

import com.challenge3.app.configuration.auth.role.ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "role"})
})
public class RoleEntity implements GrantedAuthority {

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email")
    private UserEntity userEntity;

    @NotNull(message = "Tên vai trò là bắt buộc!")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ROLE role;


    @Override
    public String getAuthority() {
        return "ROLE_" + this.role.name();
    }
}
