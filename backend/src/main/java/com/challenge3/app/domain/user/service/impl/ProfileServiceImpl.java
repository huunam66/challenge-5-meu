package com.challenge3.app.domain.user.service.impl;

import com.challenge3.app.domain.location.ward.repository.WardRepository;
import com.challenge3.app.domain.user.dto.ProfileDTO;
import com.challenge3.app.domain.user.dto.ProfileLocationDTO;
import com.challenge3.app.domain.user.repository.ProfileRepository;
import com.challenge3.app.domain.user.repository.UserRepository;
import com.challenge3.app.domain.user.service.ProfileService;
import com.challenge3.app.entity.ProfileEntity;
import com.challenge3.app.entity.ProfileLocationEntity;
import com.challenge3.app.entity.UserEntity;
import com.challenge3.app.exception.BadRequestException;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public ProfileDTO save(ProfileDTO profileDTO, String email) {

        if(profileDTO == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        ProfileEntity profileEntity = this.profileRepository.findProfileByUsername(email).orElse(null);

        if(profileEntity == null){
            UserEntity userEntity = this.userRepository.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("Người dùng không tồn tại!")
            );

            profileEntity = ProfileEntity.builder().user(userEntity).profileLocation(null).build();
        }

        profileEntity.setFirst_name(profileDTO.getFirst_name());
        profileEntity.setLast_name(profileDTO.getLast_name());
        profileEntity.setIdentification_code(profileDTO.getIdentification_code());
        profileEntity.setBirthDay(profileDTO.getBirthDay());
        profileEntity.setGender(profileDTO.getGender());


        return this.modelMapper.map(
                this.profileRepository.save(profileEntity),
                ProfileDTO.class
        );
    }

    @Override
    public ProfileLocationDTO save(ProfileLocationDTO profileLocationDTO, String email) {

//        System.out.println(profileLocationDTO);

        if(profileLocationDTO == null) throw new IsNullOrEmptyException("Đầu vào không hợp lệ!");

        ProfileEntity profileEntity = this.profileRepository.findProfileLocationByUsername(email).orElseThrow(null);

        if(profileEntity == null){
            UserEntity userEntity = this.userRepository.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("Người dùng không tồn tại!")
            );

            profileEntity = ProfileEntity.builder().user(userEntity).profileLocation(
                    ProfileLocationEntity.builder().profile(profileEntity).ward(null).build()
            ).build();
        }

        ProfileLocationEntity profileLocationEntity = profileEntity.getProfileLocation();

        if(profileLocationEntity == null){
            profileLocationEntity = ProfileLocationEntity.builder().profile(profileEntity).ward(null).build();
            profileEntity.setProfileLocation(profileLocationEntity);
        }

        profileLocationEntity.setHome_number(profileLocationDTO.getHome_number());
        profileLocationEntity.setStreet(profileLocationDTO.getStreet());
        profileLocationEntity.setCountry(profileLocationDTO.getCountry());

        if(profileLocationEntity.getWard() == null || !profileLocationEntity.getWard().getId().equals(profileLocationDTO.getWard().getId())){
            profileLocationEntity.setWard(
                    this.wardRepository.findWardDById(profileLocationDTO.getWard().getId()).orElse(null)
            );
        }

        return this.modelMapper.map(
                this.profileRepository.save(profileEntity).getProfileLocation(),
                ProfileLocationDTO.class
        );
    }


    @Override
    public void uploadAvatar(String email, MultipartFile file) throws Exception {

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
                    () -> new NotFoundException("Người dùng không tồn tại!")
            );

            profileEntity = ProfileEntity.builder().user(userEntity).profileLocation(null).build();
        }

        String imageBase64 = Base64.encodeBase64String(file.getBytes());

        profileEntity.setAvatar(imageBase64);

        this.profileRepository.save(profileEntity);
    }
}
