package com.challenge3.app.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "districts")
public class DistrictsEntity {

    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String name_en;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "full_name_en")
    private String full_name_en;

    @Column(name = "code_name")
    private String code_name;

    @JoinColumn(name = "province_id")

    @JoinColumn(name = "province_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProvinceEntity provinceEntity;

    @OneToMany(mappedBy = "districtsEntity", fetch = FetchType.LAZY)
    private List<WardEntity> wardEntities;

    @OneToMany(mappedBy = "districtsEntity", fetch = FetchType.LAZY)
    private List<ProfileLocationEntity> profileLocationEntities;
}
