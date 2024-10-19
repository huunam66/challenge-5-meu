package com.challenge3.app.domain.product.service;

import java.util.*;

import com.challenge3.app.exception.BadRequestException;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import com.challenge3.app.domain.product.response.ProductFilters;
import com.challenge3.app.domain.product.request.ProductRequest;
import com.challenge3.app.utils.Helper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.challenge3.app.entity.ProductEntity;
import com.challenge3.app.domain.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity findByCode(String code) {

        if (code == null) throw new IsNullOrEmptyException("Lỗi đầu vào nullable!");

        ProductEntity product = productRepository.findByCode(code);

        if (product == null) throw new NotFoundException("Sản phẩm có mã " + code + " không tồn tại !");

        return product;
    }

    @Override
    public ProductFilters productFilters(int limit, int page) {

        int size = productRepository.countAll();

        int countPage = Helper.calculateCountPage(limit, size);

        if (page > countPage) page = countPage;
        if (page < 1) page = 1;

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ProductEntity> pageableProducts = productRepository.findAll(pageable);

        Map<String, Object> values = new HashMap<>();
        values.put("products", pageableProducts.getContent());

        return injectProductFilterResponse(page, countPage, size, limit, values);
    }

    private ProductFilters injectProductFilterResponse(int page, int countPage, int size, int limit, Object values){
        return new ProductFilters(page, limit, size, countPage, values);
    }

    @Override
    public List<ProductEntity> search(String keyword) {
        List<ProductEntity> products = this.findAll();

        List<ProductEntity> productList = new ArrayList<>();
        String[] keywordSplit = keyword.split(" ");
        for (String keyW : keywordSplit) {
            List<ProductEntity> productListFilter = products.stream().filter(prod -> prod.getName().toUpperCase().contains(keyW.trim().toUpperCase()) && (!productList.contains(prod))).toList();
            if (productListFilter.isEmpty()) continue;
            for (ProductEntity prod : productListFilter) {
                if (productList.contains(prod)) continue;
                productList.add(prod);
            }
        }

        return productList;
    }

//    private ProductEntity injectProduct(ProductRequest productDTO) {
//
//        return ProductEntity
//                .builder()
//                    .id(productDTO.getId())
//                    .code(productDTO.getCode())
//                    .name(productDTO.getName())
//                    .brand(productDTO.getBrand())
//                    .category(productDTO.getCategory())
//                    .type(productDTO.getType())
//                    .description(productDTO.getDescription())
//                .build();
//    }

    @Transactional
    @Override
    public ProductEntity save(ProductRequest productDTO) {

        List<Long> listId = this.findAllIdDesc();
        String code;
        int zeroCodelevel = 1000;
        String firstCaseCode = "P";
        if (listId.isEmpty()) {
            int id = 1;
            code = Helper.toCode(id, zeroCodelevel, firstCaseCode);
        } else code = Helper.toCode(listId.get(0), zeroCodelevel, firstCaseCode);

        ProductEntity productEntity = this.modelMapper.map(productDTO, ProductEntity.class);
        productEntity.setCode(code);

        return productRepository.save(
                productEntity
        );
    }

    // Modify Product
    @Transactional
    @Override
    public ProductEntity save(ProductRequest productDTO, String code) {
        System.out.println("productDTO: " + productDTO);
        ProductEntity findByCode = findByCode(code);

        findByCode.setName(productDTO.getName());
        findByCode.setBrand(productDTO.getBrand());
        findByCode.setDescription(productDTO.getDescription());
        findByCode.setType(productDTO.getType());
        findByCode.setCategory(productDTO.getCategory());

        return productRepository.save(findByCode);
    }

    @Transactional
    @Override
    public String delete(String code) {

        ProductEntity product = this.findByCode(code);
        String nameOldProduct = product.getName();
        this.productRepository.delete(product);

        return nameOldProduct;
    }

    @Override
    public List<Long> findAllIdDesc() {
        return this.productRepository.findAllIdDesc();
    }

}
