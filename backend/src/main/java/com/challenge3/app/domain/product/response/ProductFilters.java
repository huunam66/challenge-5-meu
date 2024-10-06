package com.challenge3.app.domain.product.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductFilters {

    private int page;
    private int limit;
    private int size;
    private int countPage;
    private Object values;

}
