package com.challenge3.app.domain.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest {

    private Long id;

    private String code;

    @NotBlank(message = "Tên sản phẩm là bắt buộc!")
    @NotNull(message = "Tên sản phẩm là bắt buộc!")
    private String name;

    private String description;

    @NotBlank(message = "Thể loại sản phẩm là bắt buộc!")
    @NotNull(message = "Thể loại sản phẩm là bắt buộc!")
    private String category;

    @NotBlank(message = "Hãng sản phẩm là bắt buôộc!")
    @NotNull(message = "Hãng sản phẩm là bắt buôộc!")
    private String brand;

    private String type;


}
