package com.challenge3.app.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "profile_location")
public class ProfileLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id")
    private UUID id;

    @Column(name = "home_number")
    private String home_number;

    @Column(name = "street")
    private String street;

    @Column(name = "country")
    private String country;

    @JoinColumn(name = "ward_id", referencedColumnName = "id")
    @ManyToOne
    private WardEntity ward;

    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @OneToOne
    private ProfileEntity profile;

}
