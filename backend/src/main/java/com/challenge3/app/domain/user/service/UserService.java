package com.challenge3.app.domain.user.service;

import com.challenge3.app.domain.user.request.UserRequest;
import com.challenge3.app.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> findAll();
    UserEntity findByEmail(String email);
    String deleteByEmail(String email);
    UserEntity save(UserRequest userRequest);
}
