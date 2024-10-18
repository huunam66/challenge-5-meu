package com.challenge3.app.entity;

import com.challenge3.app.configuration.auth.role.ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "authorities")
@Table(name = "authorities", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "name"})
})
public class AuthoritiesEntity {

    @Id
    @NotBlank(message = "Email là bắt buộc!")
    @NotNull(message = "Email là bắt buộc!")
    @Email(message = "Email không hợp lệ!")
    @Size(min = 10, message = "Email chứa ít nhất 10 kí tự")
    @Size(max = 100, message = "Email chứa nhiều nhất 100 kí tự")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ!")
    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private UserEntity user;

    @NotNull(message = "Tên vai trò là bắt buộc!")
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private ROLE name;

}
