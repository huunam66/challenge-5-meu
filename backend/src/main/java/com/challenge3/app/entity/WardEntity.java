package com.challenge3.app.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "wards")
@Table(name = "wards")
public class WardEntity {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "name_en")
    String name_en;

    @Column(name = "full_name")
    String full_name;

    @Column(name = "full_name_en")
    String full_name_en;

    @Column(name = "code_name")
    String code_name;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    DistrictsEntity district;

    @OneToMany(mappedBy = "ward", fetch = FetchType.LAZY)
    List<ProfileLocationEntity> profileLocations;

}
