package com.challenge3.app.domain.location.ward.repository;

import com.challenge3.app.entity.WardEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WardRepository extends JpaRepository<WardEntity, String> {

    @Query("SELECT w FROM wards w WHERE w.district.id = :district_id ORDER BY w.full_name")
    List<WardEntity> findALlWardByDistrictId(@Param("district_id") String district_id);

    @EntityGraph(attributePaths = {"district"})
    @Query("SELECT w FROM wards w WHERE w.id = :id")
    Optional<WardEntity> findWardDById(@Param("id") String id);

    @EntityGraph(attributePaths = {"district", "district.province"})
    @Query("SELECT w FROM wards w WHERE w.id = :id")
    Optional<WardEntity> findWardDistrictProvinceByWardId(@Param("id") String id);

}
