package com.challenge3.app.entity;

import com.challenge3.app.configuration.auth.role.ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "role"})
})
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email")
    private UserEntity userEntity;

    @NotNull(message = "Tên vai trò là bắt buộc!")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ROLE role;
}
