package com.challenge3.app.domain.user.service;

import com.challenge3.app.domain.user.dto.ProfileDTO;
import com.challenge3.app.domain.user.dto.ProfileLocationDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    ProfileDTO save(ProfileDTO profileDTO, String email);

    ProfileLocationDTO save(ProfileLocationDTO profileLocationDTO, String email);

    void uploadAvatar(String email, MultipartFile file) throws Exception;
}
