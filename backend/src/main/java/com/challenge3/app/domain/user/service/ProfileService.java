package com.challenge3.app.domain.user.service;

import com.challenge3.app.domain.user.dto.ProfileDTO;
import com.challenge3.app.domain.user.dto.ProfileLocationDTO;
import com.challenge3.app.domain.user.request.UploadAvatar;

public interface ProfileService {

    ProfileDTO save(ProfileDTO profileDTO, String email);

    ProfileLocationDTO save(ProfileLocationDTO profileLocationDTO, String email);

    String uploadAvatar(UploadAvatar uploadAvatar) throws Exception;

    ProfileDTO getProfileAndLocationByUserEmail(String email);
}
