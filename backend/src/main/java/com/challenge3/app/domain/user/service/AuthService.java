package com.challenge3.app.domain.user.service;

import com.challenge3.app.domain.user.request.GrantRequest;
import com.challenge3.app.domain.user.request.UserRequest;

public interface AuthService {

    String signin(UserRequest userDTO);

    void grant(GrantRequest grantRequest);
}
