package com.challenge3.app.entity;


import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UpdateTimestamp;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity(name = "products")
@Table(name = "products")
public class ProductEntity {

    @Id
    @NotNull(message = "Id sản phẩm là bắt buọộc!")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "Mã code sản phẩm là bắt buộc!")
    @NotNull(message = "Mã code sản phẩm là bắt buộc!")
    @Column(name = "code", nullable = false, unique = true)
    String code;

    @NotBlank(message = "Tên sản phẩm là bắt buộc!")
    @NotNull(message = "Tên sản phẩm là bắt buộc!")
    @Column(name = "name", nullable = false)
    String name;

    @NotBlank(message = "Thể loại sản phẩm là bắt buộc!")
    @NotNull(message = "Thể loại sản phẩm là bắt buộc!")
    @Column(name = "category", nullable = false)
    String category;

    @NotBlank(message = "Hãng sản phẩm là bắt buôộc!")
    @NotNull(message = "Hãng sản phẩm là bắt buôộc!")
    @Column(name = "brand")
    String brand;

    @Column(name = "type")
    String type;

    @Column(name = "description")
    String description;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    ZonedDateTime created_at;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    ZonedDateTime updated_at;

}
