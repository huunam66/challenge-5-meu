package com.challenge3.app.domain.profile.service;

import com.challenge3.app.domain.profile.dto.ProfileDTO;
import com.challenge3.app.domain.profile.dto.ProfileLocationDTO;
import com.challenge3.app.domain.profile.request.ProfileLocationRequest;
import com.challenge3.app.domain.profile.request.ProfileRequest;
import com.challenge3.app.domain.profile.request.UploadAvatarRequest;
import com.challenge3.app.domain.profile.dto.UploadAvatarDTO;

public interface ProfileService {

    ProfileDTO save(ProfileRequest profileRequest, String email);

    ProfileLocationDTO save(ProfileLocationRequest profileLocationRequest, String email);

    UploadAvatarDTO uploadAvatar(UploadAvatarRequest uploadAvatar) throws Exception;

    ProfileDTO getProfileAndLocationByUserEmail(String email);
}
