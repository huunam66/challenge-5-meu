package com.challenge3.app.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "wards")
@Table(name = "wards")
public class WardEntity {

    @Id
    @Column(name = "id")
    private String id;

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

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private DistrictsEntity district;

    @OneToMany(mappedBy = "ward", fetch = FetchType.LAZY)
    private List<ProfileLocationEntity> profileLocations;

}
