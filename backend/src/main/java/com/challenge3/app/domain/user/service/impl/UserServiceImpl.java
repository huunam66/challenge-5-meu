package com.challenge3.app.domain.user.service.impl;

import com.challenge3.app.configuration.auth.role.ROLE;
import com.challenge3.app.domain.user.dto.UserDTO;
import com.challenge3.app.domain.user.repository.UserRepository;
import com.challenge3.app.domain.user.request.UserRequest;
import com.challenge3.app.domain.user.service.UserService;
import com.challenge3.app.entity.AuthoritiesEntity;
import com.challenge3.app.entity.UserEntity;
import com.challenge3.app.exception.IsExistedException;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserEntity> userEntities = this.userRepository.findAllUsers();

        if(userEntities.isEmpty()) throw new IsNullOrEmptyException("Danh sách nhân viên trống!");

        return this.modelMapper.map(userEntities, new TypeToken<List<UserDTO>>(){}.getType());
    }



    @Override
    public UserDTO findByEmail(String email) {
        UserEntity userEntity = this.userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Nhân viên không tồn tại!")
        );

        return this.modelMapper.map(userEntity, UserDTO.class);
    }

    @Transactional
    @Override
    public String deleteByEmail(String email) {

        UserEntity userEntity = this.userRepository.findById(email).orElseThrow(
                () -> new NotFoundException("Nhân viên không tồn tại!")
        );

        boolean isSuperAdmin = userEntity.getAuthority().getName().equals(ROLE.SUPER_ADMIN);

        if(isSuperAdmin) throw new RuntimeException("Không thể xóa nhân viên cao nhất!");

        String emailOldUser = userEntity.getEmail();

        this.userRepository.delete(userEntity);

        return emailOldUser;
    }

    @Transactional
    @Override
    public UserDTO save(UserRequest userRequest) {

        if(userRequest == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        UserEntity userEntity = this.userRepository.findById(userRequest.getEmail()).orElse(null);

        if(userEntity != null) throw new IsExistedException("Email " + userRequest.getEmail() + " tồn tại!");

        userEntity = this.userRepository.save(
                injectUserEntity(userRequest)
        );

        return this.modelMapper.map(userEntity, UserDTO.class);
    }

    private UserEntity injectUserEntity(UserRequest userRequest) {

//        System.out.println(userRequest);
        UserEntity userEntity = UserEntity
                                        .builder()
                                            .email(userRequest.getEmail())
                                            .password(this.passwordEncoder.encode(userRequest.getPassword()))
                                        .build();

        userEntity.setAuthority(
              AuthoritiesEntity.builder()
                      .user(userEntity)
                      .email(userRequest.getEmail())
                      .name(ROLE.USER)
                      .build()
        );

        return userEntity;
    }
}
