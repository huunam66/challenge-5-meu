package com.challenge3.app.domain.product.repository;

import com.challenge3.app.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByCode(@Param("code") String code);

    @Query("SELECT p.id FROM ProductEntity p order by p.id desc")
    List<Long> findAllIdDesc();

    @Query("SELECT COUNT(p) FROM ProductEntity p")
    int countAll();
}
