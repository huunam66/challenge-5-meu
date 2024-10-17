package com.challenge3.app.domain.location.province.repository;

import com.challenge3.app.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProvinceRepository extends JpaRepository<ProvinceEntity, String> {


    @Query("SELECT p FROM provinces p")
    List<ProvinceEntity> findAllProvinces();

    @Query("SELECT p FROM provinces p WHERE p.code_name = :code_name")
    Optional<ProvinceEntity> findByProvinceId(@Param("code_name") String code_name);

    @EntityGraph(attributePaths = {"districts"})
    @Query("SELECT p FROM provinces p WHERE p.code_name = :code_name")
    Optional<ProvinceEntity> findAllDistrictByProvinceId(@Param("code_name") String code_name);

}
