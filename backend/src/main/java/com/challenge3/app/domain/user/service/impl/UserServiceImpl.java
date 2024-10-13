package com.challenge3.app.domain.user.service.impl;

import com.challenge3.app.configuration.auth.role.ROLE;
import com.challenge3.app.domain.user.repository.UserRepository;
import com.challenge3.app.domain.user.request.UserRequest;
import com.challenge3.app.domain.user.service.UserService;
import com.challenge3.app.entity.RoleEntity;
import com.challenge3.app.entity.UserEntity;
import com.challenge3.app.exception.IsExistedException;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> userEntities = this.userRepository.findAll();

        if(userEntities.isEmpty()) throw new IsNullOrEmptyException("Danh sách nhân viên trống!");

        return userEntities;
    }

    @Override
    public UserEntity findByEmail(String email) {

        return this.userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Nhân viên không tồn tại!")
        );
    }

    @Transactional
    @Override
    public String deleteByEmail(String email) {

        UserEntity userEntity = this.findByEmail(email);

        boolean isSuperAdmin = userEntity.getRoleEntities().getRole().equals(ROLE.SUPER_ADMIN);

        if(isSuperAdmin) throw new RuntimeException("Không thể xóa nhân viên cao nhất!");

        String emailOldUser = userEntity.getEmail();

        this.userRepository.delete(userEntity);

        return emailOldUser;
    }

    @Transactional
    @Override
    public UserEntity save(UserRequest userRequest) {

        if(userRequest == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        UserEntity userEntity = this.userRepository.findByEmail(userRequest.getEmail()).orElse(null);

        if(userEntity != null) throw new IsExistedException("Email " + userRequest.getEmail() + " tồn tại!");

        return this.userRepository.save(
                injectUserEntity(userRequest)
        );
    }

    private UserEntity injectUserEntity(UserRequest userRequest) {

        System.out.println(userRequest);
        UserEntity userEntity = UserEntity
                                        .builder()
                                            .email(userRequest.getEmail())
                                            .password(this.passwordEncoder.encode(userRequest.getPassword()))
                                        .build();

        userEntity.setRoleEntities(
              RoleEntity.builder()
                      .userEntity(userEntity)
                      .email(userRequest.getEmail())
                      .role(ROLE.USER)
                      .build()
        );

        return userEntity;
    }
}
