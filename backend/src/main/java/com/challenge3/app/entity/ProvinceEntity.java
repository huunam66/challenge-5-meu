package com.challenge3.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "provinces")
@Table(name = "provinces")
public class ProvinceEntity {

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

    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    private List<DistrictsEntity> districts;


}
