package com.challenge3.app.domain.product.service;

import java.util.List;

import com.challenge3.app.common.dto.PageableFiltersDTO;
import com.challenge3.app.domain.product.dto.ProductDTO;
import com.challenge3.app.domain.product.request.ProductRequest;

public interface ProductService {

    List<ProductDTO> findAll();

    PageableFiltersDTO<List<ProductDTO>> productFilters(int limit, int page);


    ProductDTO findByCode(String code);

    ProductDTO save(ProductRequest productDTO);

    // Modify Product
    ProductDTO save(ProductRequest productDTO, String code);

    String delete(String code);

    List<ProductDTO> search(String keyword);

    List<Long> findAllIdDesc();


}
