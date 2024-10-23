package com.challenge3.app.domain.user.controller.auth;

import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.domain.user.dto.AuthenticatedDTO;
import com.challenge3.app.domain.user.request.GrantRequest;
import com.challenge3.app.domain.user.request.UserRequest;
import com.challenge3.app.domain.user.response.GrantSuccessfully;
import com.challenge3.app.domain.user.response.SignInSuccessfully;
import com.challenge3.app.domain.user.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@CrossOrigin({"http://localhost:8080", "http://localhost:4200"})
@RestController
@ResponseBody
@Tag(name = "Authentication")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public ApiResponse<AuthenticatedDTO> signin(@Valid @RequestBody UserRequest userDTO){

        AuthenticatedDTO authenticatedDTO = this.authService.signin(userDTO);

        String message = "Đăng nhập thành công!";

        return new SignInSuccessfully<>(message, authenticatedDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/grant", method = RequestMethod.PUT)
    public ApiResponse<Object> grant(@Valid @RequestBody GrantRequest grantRequest){

        this.authService.grant(grantRequest);

        String message = "Phân quyền cho người dùng "+grantRequest.getEmail()+" thành công!";

        return new GrantSuccessfully<>(message);
    }
}
