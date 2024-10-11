package com.challenge3.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CascadeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {

    @Id
    @NotBlank(message = "Email là bắt buộc!")
    @NotNull(message = "Email là bắt buộc!")
    @Email(message = "Email không hợp lệ!")
    @Size(min = 10, message = "Email chứa ít nhất 10 kí tự")
    @Size(max = 100, message = "Email chứa nhiều nhất 100 kí tự")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không hợp lệ!")
    @Column(name = "email", nullable = false, updatable = false, unique = true)
    private String email;


    @JsonIgnore
    @NotBlank(message = "Mật khẩu là bắt buộc!")
    @NotNull(message = "Mật khẩu là bắt buộc!")
    @Column(name = "password", nullable = false, updatable = false)
    private String password;


    @JsonManagedReference
    @OneToOne(mappedBy = "userEntity", fetch = FetchType.EAGER)
    @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    private RoleEntity roleEntities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roleEntities.stream().map(roleEntity ->
//                new SimpleGrantedAuthority("ROLE_"+roleEntity.getRole())
//        ).toList();

        return List.of(this.roleEntities);
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
