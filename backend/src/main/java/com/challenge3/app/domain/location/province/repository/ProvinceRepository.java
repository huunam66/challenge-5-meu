package com.challenge3.app.domain.location.province.repository;

import com.challenge3.app.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProvinceRepository extends JpaRepository<ProvinceEntity, String> {


    @Query("SELECT p FROM ProvinceEntity p ORDER BY p.full_name")
    List<ProvinceEntity> findAllProvinces();

    @Query("SELECT p FROM ProvinceEntity p WHERE p.id = :id")
    Optional<ProvinceEntity> findByProvinceId(@Param("id") String id);

    @EntityGraph(attributePaths = {"districts"})
    @Query("SELECT p FROM ProvinceEntity p WHERE p.id = :id")
    Optional<ProvinceEntity> findAllDistrictByProvinceId(@Param("id") String id);

}
