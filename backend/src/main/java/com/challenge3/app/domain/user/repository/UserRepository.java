package com.challenge3.app.domain.user.repository;

import com.challenge3.app.domain.user.dto.UserDTO;
import com.challenge3.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {


    @Query("SELECT u FROM users u INNER JOIN FETCH u.authority WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM users u LEFT JOIN FETCH u.authority")
    List<UserEntity> findAllUsers();
}
