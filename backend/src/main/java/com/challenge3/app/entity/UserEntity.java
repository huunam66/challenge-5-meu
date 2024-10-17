package com.challenge3.app.entity;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "users")
@Table(name = "users")
public class UserEntity {

    @Id
    @NotBlank(message = "Email là bắt buộc!")
    @NotNull(message = "Email là bắt buộc!")
    @Email(message = "Email không hợp lệ!")
    @Size(min = 10, message = "Email chứa ít nhất 10 kí tự")
    @Size(max = 100, message = "Email chứa nhiều nhất 100 kí tự")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ!")
    @Column(name = "email", nullable = false, updatable = false, unique = true)
    private String email;

    @NotBlank(message = "Mật khẩu là bắt buộc!")
    @NotNull(message = "Mật khẩu là bắt buộc!")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    private ProfileEntity profile;

    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    private AuthoritiesEntity authority;

}
