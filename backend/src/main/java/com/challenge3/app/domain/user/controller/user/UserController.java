package com.challenge3.app.domain.user.controller.user;

import com.challenge3.app.common.response.DeletedSuccessfully;
import com.challenge3.app.common.response.IsFound;
import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.domain.user.dto.UserDTO;
import com.challenge3.app.domain.user.request.UserAuthorityRequest;
import com.challenge3.app.domain.user.response.SignUpSuccessfully;
import com.challenge3.app.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin({"http://localhost:8080", "http://localhost:4200"})
@ResponseBody
@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse<Object> save(@Valid @RequestBody UserAuthorityRequest userAuthorityRequest){

        this.userService.save(userAuthorityRequest);

        String message = "Tạo mới tài khoản thành công!";

        return new SignUpSuccessfully<>(message);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse<List<UserDTO>> findAll(){

        List<UserDTO> userModels = this.userService.findAll();

        String message = "Danh sách người dùng hiện có!";

        return new IsFound<>(message, userModels);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{email}", method = RequestMethod.GET)
    public ApiResponse<UserDTO> findByEmail(@PathVariable(name = "email") String email){

        UserDTO userModel = this.userService.findByEmail(email);

        String message = "Người dùng " + userModel.getEmail() + " tồn tại!";

        return new IsFound<>(message, userModel);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{email}", method = RequestMethod.DELETE)
    public ApiResponse<Object> deleteByEmail(@PathVariable(name = "email") String email){

        this.userService.deleteByEmail(email);

        String message = "Đã xóa người dùng " + email + " thành công!";

        return new DeletedSuccessfully<>(message);
    }



}
