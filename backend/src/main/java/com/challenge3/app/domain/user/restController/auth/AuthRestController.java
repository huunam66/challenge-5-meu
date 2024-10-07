package com.challenge3.app.domain.user.restController.auth;

import com.challenge3.app.common.response.ResponseAPI;
import com.challenge3.app.domain.user.request.GrantRequest;
import com.challenge3.app.domain.user.request.UserRequest;
import com.challenge3.app.domain.user.response.GrantSuccessfully;
import com.challenge3.app.domain.user.response.SignInSuccessfully;
import com.challenge3.app.domain.user.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;



@CrossOrigin
@RestController
@ResponseBody
@Tag(name = "Authentication")
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public ResponseAPI signin(@Valid @RequestBody UserRequest userDTO){

        String token = this.authService.signin(userDTO);

//        System.out.println(token);

        String message = "Đăng nhập thành công!";

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("token", token);
        responseEntity.put("authentication", true);

        return new SignInSuccessfully(message, responseEntity);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/grant", method = RequestMethod.PUT)
    public ResponseAPI grant(@Valid @RequestBody GrantRequest grantRequest){

        this.authService.grant(grantRequest);

        String message = "Phân quyền cho người dùng "+grantRequest.getEmail()+" thành công!";

        return new GrantSuccessfully(message);
    }
}
