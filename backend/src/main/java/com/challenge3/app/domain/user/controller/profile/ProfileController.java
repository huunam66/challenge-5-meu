package com.challenge3.app.domain.user.controller.profile;


import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.common.response.SavedSuccessfully;
import com.challenge3.app.domain.user.dto.ProfileDTO;
import com.challenge3.app.domain.user.dto.ProfileLocationDTO;
import com.challenge3.app.domain.user.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin({"http://localhost:8080", "http://localhost:4200"})
@ResponseBody
@RestController
@RequestMapping("/profile")
@Tag(name = "Profile")
public class ProfileController {

    private  final ProfileService profileService;

    public ProfileController(final ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(path = "/{email}", method = RequestMethod.POST)
    public ApiResponse<Map<String, Object>> saveProfile(
            @RequestBody ProfileDTO requestProfile,
            @PathVariable(name = "email")  String email
    ) {

        ProfileDTO profileDTO = this.profileService.save(requestProfile, email);

        String message = "Cập nhật thông tin người dùng thành công!";

        Map<String, Object> response = new HashMap<>();
        response.put("profile", profileDTO);

        return new SavedSuccessfully<>(message, response);
    }

    @RequestMapping(path = "/{email}/location", method = RequestMethod.POST)
    public ApiResponse<Map<String, Object>> saveLocationProfile(
            @RequestBody ProfileLocationDTO requestProfileLocation,
            @PathVariable(name = "email") String email
    ){

        ProfileLocationDTO profileLocationDTO = this.profileService.save(requestProfileLocation, email);

        String message = "Cập nhật vị trí người dùng thành công!";

        Map<String, Object> response = new HashMap<>();
        response.put("location", profileLocationDTO);

        return new SavedSuccessfully<>(message, response);
    }

    @RequestMapping(path ="/{email}/upload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ApiResponse<Map<String, Object>> uploadAvatar(
            @PathVariable(name = "email") String email,
            @RequestParam("avatar") MultipartFile file) throws Exception {

        this.profileService.uploadAvatar(email, file);

        String message = "Tải ảnh đại diện thành công!";

        Map<String, Object> response = new HashMap<>();
        response.put("uploaded", true);

        return new SavedSuccessfully<>(message, response);
    }
}
