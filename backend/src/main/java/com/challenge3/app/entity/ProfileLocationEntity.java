package com.challenge3.app.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "profile_location")
public class ProfileLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id")
    UUID id;

    @Column(name = "home_number")
    String home_number;

    @Column(name = "street")
    String street;

    @Column(name = "country")
    String country;

    @JoinColumn(name = "ward_id", referencedColumnName = "id")
    @ManyToOne
    WardEntity ward;

    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @OneToOne
    ProfileEntity profile;

}
