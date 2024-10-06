package com.challenge3.app.domain.user.service.impl;

import com.challenge3.app.configuration.auth.jwt.JwtService;
import com.challenge3.app.configuration.auth.role.ROLE;
import com.challenge3.app.configuration.auth.role.ROLE_STORE;
import com.challenge3.app.domain.user.request.GrantRequest;
import com.challenge3.app.domain.user.service.AuthService;
import com.challenge3.app.entity.RoleEntity;
import com.challenge3.app.entity.UserEntity;
import com.challenge3.app.domain.user.request.UserRequest;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.domain.user.repository.UserRepository;
import com.challenge3.app.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           AuthenticationManager authenticationManager,
                           UserDetailsService userDetailsService,
                           JwtService jwtService) {

        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    public String signin(UserRequest userDTO) throws AuthenticationException {

        if(userDTO == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getEmail());

        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(), userDTO.getPassword()
                )
        );

        return jwtService.generateToken(userDetails);
    }

    @Transactional
    @Override
    public void grant(GrantRequest grantRequest) {

        if(grantRequest == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        UserEntity userEntity = userRepository.findByEmail(grantRequest.getEmail()).orElseThrow(
                () -> new NotFoundException("Email không tồn tại!")
        );

        boolean isSuperAdmin = userEntity.getRoleEntities().stream().map(RoleEntity::getRole).toList().contains(ROLE.SUPER_ADMIN);

        if(isSuperAdmin) throw new RuntimeException("Không thể phân quyền cho nhân viên cao nhất!");

        ROLE[] roles = ROLE_STORE.USER;
        if(grantRequest.getRole().equals(ROLE.ADMIN))
            roles = ROLE_STORE.ADMIN;

        userEntity.getRoleEntities().clear();

        System.out.println(userEntity);

        this.userRepository.saveAndFlush(userEntity);

        grant(userEntity, roles);
    }

    private void grant(UserEntity userEntity, ROLE[] roles){
        userEntity.getRoleEntities().addAll(
                Arrays.stream(roles).map(role ->
                        RoleEntity.builder()
                                .userEntity(userEntity)
                                .role(role)
                                .build()
                        ).toList()
        );

        this.userRepository.save(userEntity);
    }
}
