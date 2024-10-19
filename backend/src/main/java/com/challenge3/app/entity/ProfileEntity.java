package com.challenge3.app.entity;

import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "profile")
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id")
    UUID id;

    @Column(name = "first_name")
    String first_name;

    @Column(name = "last_name")
    String last_name;

    @Column(name = "identification_code")
    String identification_code;

    @Column(name = "birthday")
    Date birthDay;

    @Column(name = "gender")
    String gender;

    @Column(name = "avatar")
    String avatar;

    @Setter(AccessLevel.NONE)
    @Column(name = "email", insertable=false, updatable=false)
    String email;

    @JoinColumn(name = "email", referencedColumnName = "email")
    @OneToOne
    UserEntity user;

    @OneToOne(mappedBy = "profile")
    @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    ProfileLocationEntity profileLocation;
}
