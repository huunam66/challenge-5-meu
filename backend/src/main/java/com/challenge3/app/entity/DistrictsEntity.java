package com.challenge3.app.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "districts")
public class DistrictsEntity {

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
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    ProvinceEntity province;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    List<WardEntity> wards;


}
