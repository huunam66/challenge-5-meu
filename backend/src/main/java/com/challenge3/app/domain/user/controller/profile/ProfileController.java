package com.challenge3.app.domain.user.controller.profile;


import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.common.response.IsFound;
import com.challenge3.app.common.response.SavedSuccessfully;
import com.challenge3.app.domain.user.dto.ProfileDTO;
import com.challenge3.app.domain.user.dto.ProfileLocationDTO;
import com.challenge3.app.domain.user.request.UploadAvatar;
import com.challenge3.app.domain.user.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
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

    @ResponseStatus(HttpStatus.CREATED)
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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path ="/{email}/upload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ApiResponse<Map<String, Object>> uploadAvatar(
            @PathVariable(name = "email") String email,
            @RequestParam(name = "file") MultipartFile file
    ) throws Exception {

        String avartar = this.profileService.uploadAvatar(
                UploadAvatar.builder().email(email).file(file).build()
        );

        String message = "Tải ảnh đại diện thành công!";

        Map<String, Object> response = new HashMap<>();
        response.put("avatar", avartar);
        response.put("uploaded", true);

        return new SavedSuccessfully<>(message, response);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ApiResponse<Map<String, Object>> getProfile(
            @PathVariable(name = "email") String email
    ){
        System.out.println("HELOOOOOOOOOOOO");
        ProfileDTO profileDTO = this.profileService.getProfileAndLocationByUserEmail(email);

        String message = "Thông tin người dùng " + email + " hiện có!";

        Map<String, Object> response = new HashMap<>();
        response.put("profile", profileDTO);

        return new IsFound<>(message, response);
    }
}
