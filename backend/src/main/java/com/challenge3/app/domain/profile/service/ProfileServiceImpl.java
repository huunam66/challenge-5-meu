package com.challenge3.app.domain.profile.service;

import com.challenge3.app.domain.location.ward.repository.WardRepository;
import com.challenge3.app.domain.profile.dto.ProfileDTO;
import com.challenge3.app.domain.profile.dto.ProfileLocationDTO;
import com.challenge3.app.domain.profile.dto.UploadAvatarDTO;
import com.challenge3.app.domain.profile.repository.ProfileRepository;
import com.challenge3.app.domain.profile.request.ProfileLocationRequest;
import com.challenge3.app.domain.profile.request.ProfileRequest;
import com.challenge3.app.domain.user.repository.UserRepository;
import com.challenge3.app.domain.profile.request.UploadAvatarRequest;
import com.challenge3.app.entity.ProfileEntity;
import com.challenge3.app.entity.UserLocationEntity;
import com.challenge3.app.entity.UserEntity;
import com.challenge3.app.exception.BadRequestException;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final WardRepository wardRepository;
    private final ModelMapper modelMapper;

    public ProfileServiceImpl(UserRepository userRepository, ProfileRepository profileRepository, WardRepository wardRepository, ModelMapper modelMapper) {
        this.profileRepository = profileRepository;
        this.modelMapper = modelMapper;
        this.wardRepository = wardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProfileDTO save(ProfileRequest profileRequest, String email) {

        if(profileRequest == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        ProfileEntity profileEntity = this.profileRepository.findProfileByUsername(email).orElse(null);

        if(profileEntity == null){
            UserEntity userEntity = this.userRepository.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("Người dùng không tồn tại!")
            );

            profileEntity = ProfileEntity.builder().user(userEntity).profileLocation(null).build();
        }

        profileEntity.setFirst_name(profileRequest.getFirst_name());
        profileEntity.setLast_name(profileRequest.getLast_name());
        profileEntity.setIdentification_code(profileRequest.getIdentification_code());
        profileEntity.setBirthDay(profileRequest.getBirthDay());
        profileEntity.setGender(profileRequest.getGender());

        return this.modelMapper.map(
                this.profileRepository.save(profileEntity),
                ProfileDTO.class
        );
    }

    @Override
    public ProfileLocationDTO save(ProfileLocationRequest profileLocationRequest, String email) {

//        System.out.println(profileLocationDTO);

        if(profileLocationRequest == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        ProfileEntity profileEntity = this.profileRepository.findProfileLocationByUsername(email).orElseThrow(null);

        if(profileEntity == null){
            UserEntity userEntity = this.userRepository.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("Người dùng "+email+" không tồn tại!")
            );

            profileEntity = ProfileEntity.builder().user(userEntity).build();
        }

        List<UserLocationEntity> profileLocationEnties = profileEntity.getProfileLocation();

        UserLocationEntity profileLocationEntity = new UserLocationEntity();
        profileLocationEntity.setProfile(profileEntity);
        profileLocationEntity.setHome_number(profileLocationRequest.getHome_number());
        profileLocationEntity.setStreet(profileLocationRequest.getStreet());
        profileLocationEntity.setCountry("Việt Nam");

        if(profileLocationEntity.getWard() == null
                || !profileLocationEntity.getWard().getId().equals(profileLocationRequest.getWardId())){

            profileLocationEntity.setWard(
                    this.wardRepository.findWardDById(profileLocationRequest.getWardId()).orElseThrow(
                            () -> new NotFoundException("Phường/xã không tồn tại!")
                    )
            );
        }

        profileLocationEnties.add(profileLocationEntity);

        return this.modelMapper.map(
                this.profileRepository.save(profileEntity).getProfileLocation(),
                ProfileLocationDTO.class
        );
    }


    @Override
    public UploadAvatarDTO uploadAvatar(UploadAvatarRequest uploadAvatar) throws Exception {

        String email = uploadAvatar.getEmail();
        MultipartFile file = uploadAvatar.getFile();

        if(file == null || file.isEmpty())
            throw new BadRequestException("File không hợp lệ!");

        String contentType = file.getContentType();

        assert contentType != null;
        boolean isImageFile = contentType.equals("image/jpeg")
                || contentType.equals("image/png")
                || contentType.equals("image/gif")
                || contentType.equals("image/jpg");

        if(!isImageFile) throw new BadRequestException("Định dạng file không hợp lệ!");

        ProfileEntity profileEntity = this.profileRepository.findProfileByUsername(email).orElse(null);

        if(profileEntity == null){
            UserEntity userEntity = this.userRepository.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("Người dùng " +uploadAvatar.getEmail()+" không tồn tại!")
            );

            profileEntity = ProfileEntity.builder().user(userEntity).profileLocation(null).build();
        }

        String imageBase64 = Base64.encodeBase64String(file.getBytes());

        profileEntity.setAvatar(imageBase64);

        return new UploadAvatarDTO(
                this.profileRepository.save(profileEntity).getAvatar(),
                true
        );
    }

    @Override
    public ProfileDTO getProfileAndLocationByUserEmail(String email) {

        System.out.println("HELLOOOOOO");
        ProfileEntity profileEntity = this.profileRepository.findProfileLocationByUsername(email).orElseThrow(
                () -> new IsNullOrEmptyException("Thông tin người dùng " + email + " không tồn tại!")
        );

        return this.modelMapper.map(profileEntity, ProfileDTO.class);
    }
}
