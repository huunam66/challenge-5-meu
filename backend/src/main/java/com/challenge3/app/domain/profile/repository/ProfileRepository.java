package com.challenge3.app.domain.profile.repository;

import com.challenge3.app.entity.ProfileEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {

    @Query("SELECT p FROM profile p WHERE p.user.email = :username")
    Optional<ProfileEntity> findProfileByUsername(@Param("username") String username);

    @EntityGraph(
            attributePaths = {
                    "profileLocation",
                    "profileLocation.ward",
                    "profileLocation.ward.district",
                    "profileLocation.ward.district.province"
            })
    @Query("SELECT p FROM profile p WHERE p.user.email = :username")
    Optional<ProfileEntity> findProfileLocationByUsername(@Param("username") String username);
}
