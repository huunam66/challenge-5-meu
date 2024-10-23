package com.challenge3.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    String email;

    @JsonIgnore
    @NotBlank(message = "Mật khẩu là bắt buộc!")
    @NotNull(message = "Mật khẩu là bắt buộc!")
    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "enabled", nullable = false)
    boolean enabled;

    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    ProfileEntity profile;

    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    AuthoritiesEntity authority;

}
