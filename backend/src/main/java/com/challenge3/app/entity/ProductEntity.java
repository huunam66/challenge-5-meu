package com.challenge3.app.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "products")
@Table(name = "products")
public class ProductEntity {

    @Id
    @NotNull(message = "Id sản phẩm là bắt buọộc!")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Mã code sản phẩm là bắt buộc!")
    @NotNull(message = "Mã code sản phẩm là bắt buộc!")
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotBlank(message = "Tên sản phẩm là bắt buộc!")
    @NotNull(message = "Tên sản phẩm là bắt buộc!")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Thể loại sản phẩm là bắt buộc!")
    @NotNull(message = "Thể loại sản phẩm là bắt buộc!")
    @Column(name = "category", nullable = false)
    private String category;

    @NotBlank(message = "Hãng sản phẩm là bắt buôộc!")
    @NotNull(message = "Hãng sản phẩm là bắt buôộc!")
    @Column(name = "brand")
    private String brand;

    @Nullable
    @Column(name = "type")
    private String type;

    @Nullable
    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updated_at;

}
