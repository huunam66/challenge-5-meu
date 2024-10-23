package com.challenge3.app.domain.user.service;

import com.challenge3.app.domain.user.request.GrantRequest;
import com.challenge3.app.domain.user.request.UserRequest;
import com.challenge3.app.domain.user.dto.AuthenticatedDTO;

public interface AuthService {

    AuthenticatedDTO signin(UserRequest userDTO);

    void grant(GrantRequest grantRequest);
}
