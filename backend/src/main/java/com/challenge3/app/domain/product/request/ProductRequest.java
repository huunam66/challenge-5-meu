package com.challenge3.app.domain.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductRequest {

    @Schema(example = "Sản phẩm 01", description = "Tên sản phẩm")
    @NotBlank(message = "Tên sản phẩm là bắt buộc!")
    @NotNull(message = "Tên sản phẩm là bắt buộc!")
    String name;

    @Schema(example = "Sản phẩm tốt", description = "Mô tả sản phẩm")
    String description;

    @Schema(example = "Thể loại 1", description = "Thể loại sản phẩm")
    @NotBlank(message = "Thể loại sản phẩm là bắt buộc!")
    @NotNull(message = "Thể loại sản phẩm là bắt buộc!")
    String category;

    @Schema(example = "Hãng 1", description = "Hãng sản phẩm")
    @NotBlank(message = "Hãng sản phẩm là bắt buôộc!")
    @NotNull(message = "Hãng sản phẩm là bắt buôộc!")
    String brand;

    @Schema(example = "Loại 1", description = "Loại sản phẩm")
    String type;


}
