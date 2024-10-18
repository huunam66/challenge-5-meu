package com.challenge3.app.domain.user.service;

import com.challenge3.app.domain.user.dto.UserDTO;
import com.challenge3.app.domain.user.request.UserAuthorityRequest;
import com.challenge3.app.domain.user.request.UserRequest;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();
    UserDTO findByEmail(String email);
    String deleteByEmail(String email);
    UserDTO save(UserAuthorityRequest userAuthorityRequest);
}
