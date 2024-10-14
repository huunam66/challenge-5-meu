package com.challenge3.app.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profile_location")
public class ProfileLocationEntity {

    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @Column(name = "home_number")
    private String home_number;

    @Column(name = "street")
    private String street;

    @Column(name = "country")
    private String country;

    @JoinColumn(name = "ward_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private WardEntity wardEntity;

    @JoinColumn(name = "district_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private DistrictsEntity districtsEntity;

    @JoinColumn(name = "province_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProvinceEntity provinceEntity;

    @JoinColumn(name = "profile_id")
    @OneToOne(fetch = FetchType.EAGER)
    private ProfileEntity profileEntity;

}
