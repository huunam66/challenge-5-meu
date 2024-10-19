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
@Entity(name = "provinces")
@Table(name = "provinces")
public class ProvinceEntity {

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

    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    List<DistrictsEntity> districts;


}
