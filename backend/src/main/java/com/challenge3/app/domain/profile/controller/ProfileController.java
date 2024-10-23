package com.challenge3.app.domain.profile.controller;


import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.common.response.IsFound;
import com.challenge3.app.common.response.SavedSuccessfully;
import com.challenge3.app.domain.profile.dto.ProfileDTO;
import com.challenge3.app.domain.profile.dto.ProfileLocationDTO;
import com.challenge3.app.domain.profile.request.ProfileLocationRequest;
import com.challenge3.app.domain.profile.request.ProfileRequest;
import com.challenge3.app.domain.profile.request.UploadAvatarRequest;
import com.challenge3.app.domain.profile.dto.UploadAvatarDTO;
import com.challenge3.app.domain.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ApiResponse<ProfileDTO> saveProfile(
            @RequestBody ProfileRequest profileRequest,
            @PathVariable(name = "email")  String email
    ) {

        ProfileDTO profileDTO = this.profileService.save(profileRequest, email);

        String message = "Cập nhật thông tin người dùng thành công!";

        return new SavedSuccessfully<>(message, profileDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/{email}/location", method = RequestMethod.POST)
    public ApiResponse<ProfileLocationDTO> saveLocationProfile(
            @RequestBody ProfileLocationRequest profileLocationRequest,
            @PathVariable(name = "email") String email
    ){

        ProfileLocationDTO profileLocationDTO = this.profileService.save(profileLocationRequest, email);

        String message = "Cập nhật vị trí người dùng thành công!";

        return new SavedSuccessfully<>(message, profileLocationDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path ="/{email}/upload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ApiResponse<UploadAvatarDTO> uploadAvatar(
            @PathVariable(name = "email") String email,
            @RequestParam(name = "file") MultipartFile file
    ) throws Exception {

        UploadAvatarDTO avatarResponse = this.profileService.uploadAvatar(
                UploadAvatarRequest.builder().email(email).file(file).build()
        );

        String message = "Tải ảnh đại diện thành công!";

        return new SavedSuccessfully<>(message, avatarResponse);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ApiResponse<ProfileDTO> getProfile(
            @PathVariable(name = "email") String email
    ){

        ProfileDTO profileDTO = this.profileService.getProfileAndLocationByUserEmail(email);

        String message = "Thông tin người dùng " + email + " hiện có!";

        return new IsFound<>(message, profileDTO);
    }
}
