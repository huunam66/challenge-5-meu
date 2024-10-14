package com.challenge3.app.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.hibernate.annotations.CascadeType;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
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

    @Column(name = "email")
    private String email;

    @JoinColumn(name = "email")
    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity userEntity;


    @OneToOne(mappedBy = "profileEntity", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private ProfileLocationEntity profileLocationEntity;
}
