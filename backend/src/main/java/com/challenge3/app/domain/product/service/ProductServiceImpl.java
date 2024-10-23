package com.challenge3.app.domain.product.service;

import java.lang.reflect.Type;
import java.util.*;

import com.challenge3.app.common.dto.PageableFiltersDTO;
import com.challenge3.app.domain.product.dto.ProductDTO;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import com.challenge3.app.domain.product.request.ProductRequest;
import com.challenge3.app.utils.Helper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
    public List<ProductDTO> findAll() {
        List<ProductEntity> productEntities = productRepository.findAll();

        Type typeListProductDTO = new TypeToken<List<ProductDTO>>(){}.getType();

        return modelMapper.map(productEntities, typeListProductDTO);
    }

    @Override
    public ProductDTO findByCode(String code) {

        if (code == null) throw new IsNullOrEmptyException("Lỗi đầu vào nullable!");

        ProductEntity product = productRepository.findByCode(code);

        if (product == null) throw new NotFoundException("Sản phẩm có mã " + code + " không tồn tại !");

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public PageableFiltersDTO<List<ProductDTO>> productFilters(int limit, int page) {

        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<ProductEntity> pageableProducts = productRepository.findAll(pageable);

        int pageNumber = pageableProducts.getPageable().getPageNumber() + 1;
        int totalPages = pageableProducts.getTotalPages();
        long offset = pageableProducts.getPageable().getOffset();
        long totalElements = pageableProducts.getTotalElements();
        boolean hasPreviousPage = pageNumber > 1;
        boolean hasNextPage = pageNumber < totalPages;

        Type typeListProductDTO = new TypeToken<List<ProductDTO>>(){}.getType();

        return new PageableFiltersDTO<>(
                pageNumber,
                totalPages,
                offset,
                totalElements,
                hasPreviousPage,
                hasNextPage,
                modelMapper.map(pageableProducts.getContent(), typeListProductDTO)
        );
    }

    @Override
    public List<ProductDTO> search(String keyword) {
        List<ProductEntity> products = this.productRepository.findAll();

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

        return this.modelMapper.map(
                productList,
                new TypeToken<List<ProductDTO>>(){}.getType()
        );
    }

    @Transactional
    @Override
    public ProductDTO save(ProductRequest productDTO) {

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

        return this.modelMapper.map(
                productRepository.save(
                        productEntity
                ),
                ProductDTO.class
        );
    }

    // Modify Product
    @Transactional
    @Override
    public ProductDTO save(ProductRequest productDTO, String code) {
        System.out.println("productDTO: " + productDTO);
        ProductEntity findByCode = this.productRepository.findByCode(code);

        findByCode.setName(productDTO.getName());
        findByCode.setBrand(productDTO.getBrand());
        findByCode.setDescription(productDTO.getDescription());
        findByCode.setType(productDTO.getType());
        findByCode.setCategory(productDTO.getCategory());

        return this.modelMapper.map(
                productRepository.save(findByCode),
                ProductDTO.class
        );
    }

    @Transactional
    @Override
    public String delete(String code) {

        ProductEntity product = this.productRepository.findByCode(code);
        String nameOldProduct = product.getName();
        this.productRepository.delete(product);

        return nameOldProduct;
    }

    @Override
    public List<Long> findAllIdDesc() {
        return this.productRepository.findAllIdDesc();
    }

}
