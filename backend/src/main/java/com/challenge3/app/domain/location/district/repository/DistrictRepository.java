package com.challenge3.app.domain.location.district.repository;

import com.challenge3.app.entity.DistrictsEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<DistrictsEntity, String> {

    @Query("SELECT d FROM districts d WHERE d.province.id = :province_id ORDER BY d.full_name")
    List<DistrictsEntity> findAllDistrictsByProvinceId(@Param("province_id") String province_id);

    @EntityGraph(attributePaths = {"province"})
    @Query("SELECT d FROM districts d WHERE d.id = :district_id")
    Optional<DistrictsEntity> findDistrictById(@Param("district_id") String district_id);

    @EntityGraph(attributePaths = {"wards"})
    @Query("SELECT d FROM districts d WHERE d.id = :district_id")
    Optional<DistrictsEntity> findWardsExcludeProvinceByDistrictId(@Param("district_id") String district_id);

    @EntityGraph(attributePaths = {"province", "wards"})
    @Query("SELECT d FROM districts d WHERE d.id = :district_id")
    Optional<DistrictsEntity> findWardsByDistrictId(@Param("district_id") String district_id);
}
