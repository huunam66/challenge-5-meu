package com.challenge3.app.entity;

import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "profile")
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "identification_code")
    private String identification_code;

    @Column(name = "birthday")
    private Date birthDay;

    @Column(name = "gender")
    private String gender;

    @Column(name = "avatar")
    private String avatar;

    @Setter(AccessLevel.NONE)
    @Column(name = "email", insertable=false, updatable=false)
    private String email;

    @JoinColumn(name = "email", referencedColumnName = "email")
    @OneToOne
    private UserEntity user;

    @OneToOne(mappedBy = "profile")
    @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    private ProfileLocationEntity profileLocation;
}
