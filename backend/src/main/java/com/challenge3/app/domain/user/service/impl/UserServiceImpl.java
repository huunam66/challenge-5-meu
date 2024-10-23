package com.challenge3.app.domain.user.service.impl;

import com.challenge3.app.configuration.auth.role.ROLE;
import com.challenge3.app.domain.user.dto.UserDTO;
import com.challenge3.app.domain.user.repository.UserRepository;
import com.challenge3.app.domain.user.request.UserAuthorityRequest;
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
    public void deleteByEmail(String email) {

        UserEntity userEntity = this.userRepository.findById(email).orElseThrow(
                () -> new NotFoundException("Nhân viên không tồn tại!")
        );

        boolean isSuperAdmin = userEntity.getAuthority().getName().equals(ROLE.SUPER_ADMIN);

        if(isSuperAdmin) throw new RuntimeException("Không thể xóa nhân viên cao nhất!");

        this.userRepository.delete(userEntity);
    }

    @Transactional
    @Override
    public UserDTO save(UserAuthorityRequest userAuthorityRequest) {

//        System.out.println(userAuthorityRequest);

        if(userAuthorityRequest == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        UserEntity userEntity = this.userRepository.findById(userAuthorityRequest.getEmail()).orElse(null);

        if(userEntity != null) throw new IsExistedException("Email " + userAuthorityRequest.getEmail() + " tồn tại!");

//        System.out.println(userAuthorityRequest);
        userEntity = UserEntity
                .builder()
                .email(userAuthorityRequest.getEmail())
                .password(this.passwordEncoder.encode(userAuthorityRequest.getPassword()))
                .enabled(true)
                .build();

//        System.out.println("isvaid password: " + passwordEncoder.matches(userAuthorityRequest.getPassword(), userEntity.getPassword()));

        userEntity.setAuthority(
                AuthoritiesEntity.builder()
                        .user(userEntity)
                        .email(userAuthorityRequest.getEmail())
                        .name(userAuthorityRequest.getRole())
                        .build()
        );

        return this.modelMapper.map(
                this.userRepository.save(userEntity),
                UserDTO.class
        );
    }
}
