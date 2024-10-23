package com.challenge3.app.domain.user.service.impl;

import com.challenge3.app.configuration.auth.jwt.JwtService;
import com.challenge3.app.configuration.auth.role.ROLE;
import com.challenge3.app.domain.user.dto.AuthorityDTO;
import com.challenge3.app.domain.user.dto.UserDTO;
import com.challenge3.app.domain.user.request.GrantRequest;
import com.challenge3.app.domain.user.dto.AuthenticatedDTO;
import com.challenge3.app.domain.user.service.AuthService;
import com.challenge3.app.entity.UserEntity;
import com.challenge3.app.domain.user.request.UserRequest;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.domain.user.repository.UserRepository;
import com.challenge3.app.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {

        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public AuthenticatedDTO signin(UserRequest userRequest) throws AuthenticationException {

        System.out.println(userRequest);

        if(userRequest == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(), userRequest.getPassword()
                )
        );

        String token = jwtService.generateToken(
                UserDTO.builder().email(authentication.getName()).authority(
                        AuthorityDTO.builder().name(
                                ROLE.valueOf(authentication.getAuthorities().stream().toList().get(0).getAuthority().split("ROLE_")[1])
                        ).build()
                ).build()
        );

        return AuthenticatedDTO
                .builder()
                    .token(token)
                    .authenticated(true)
                .build();
    }

    @Transactional
    @Override
    public void grant(GrantRequest grantRequest) {

        if(grantRequest == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        UserEntity userEntity = userRepository.findById(grantRequest.getEmail()).orElseThrow(
                () -> new NotFoundException("Email không tồn tại!")
        );

        boolean isSuperAdmin = userEntity.getAuthority().getName().equals(ROLE.SUPER_ADMIN);

        if(isSuperAdmin) throw new RuntimeException("Không thể phân quyền cho nhân viên cao nhất!");

        userEntity.getAuthority().setName(grantRequest.getRole());

        this.userRepository.save(userEntity);
    }
}
