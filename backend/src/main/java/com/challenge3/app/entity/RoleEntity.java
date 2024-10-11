package com.challenge3.app.entity;

import com.challenge3.app.configuration.auth.role.ROLE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Email là bắt buộc!")
    @NotNull(message = "Email là bắt buộc!")
    @Email(message = "Email không hợp lệ!")
    @Size(min = 10, message = "Email chứa ít nhất 10 kí tự")
    @Size(max = 100, message = "Email chứa nhiều nhất 100 kí tự")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ!")
    private String email;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email")
    @JsonBackReference
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
