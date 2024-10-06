package com.challenge3.app.domain.product.service;

import java.util.List;

import com.challenge3.app.entity.ProductEntity;
import com.challenge3.app.domain.product.response.ProductFilters;
import com.challenge3.app.domain.product.request.ProductRequest;

public interface ProductService {

    List<ProductEntity> findAll();

    ProductFilters productFilters(int limit, int page);


    ProductEntity findByCode(String code);

    ProductEntity save(ProductRequest productDTO);

    // Modify Product
    ProductEntity save(ProductRequest productDTO, String code);

    String delete(String code);

    List<ProductEntity> search(String keyword);

    List<Long> findAllIdDesc();


}
