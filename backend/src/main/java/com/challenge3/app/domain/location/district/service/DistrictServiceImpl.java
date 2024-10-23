package com.challenge3.app.domain.location.district.service;

import com.challenge3.app.domain.location.district.dto.DistrictDTO;
import com.challenge3.app.domain.location.district.dto.DistrictProvinceDTO;
import com.challenge3.app.domain.location.district.dto.DistrictProvinceWardsDTO;
import com.challenge3.app.domain.location.district.dto.DistrictWardsDTO;
import com.challenge3.app.domain.location.district.repository.DistrictRepository;
import com.challenge3.app.entity.DistrictsEntity;
import com.challenge3.app.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService{

    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;

    public DistrictServiceImpl(DistrictRepository districtRepository, ModelMapper modelMapper) {
        this.districtRepository = districtRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DistrictDTO> findAllDistrictsByProvinceId(String province_id, boolean province_include) {

//        System.out.println("province_code_name:"+province_id);

        List<DistrictsEntity> districtsEntities = this.districtRepository.findAllDistrictsByProvinceId(province_id);

        return this.modelMapper.map(districtsEntities,
                !province_include
                        ?
                        new TypeToken<List<DistrictDTO>>(){}.getType()
                        :
                        new TypeToken<List<DistrictProvinceDTO>>(){}.getType()
                );
    }

    @Override
    public DistrictDTO findDistrictById(String district_id) {

        DistrictsEntity districtsEntity = this.districtRepository.findDistrictById(district_id).orElseThrow(
                () -> new NotFoundException("Phường xã hiện không tồn tại!")
        );

        return modelMapper.map(districtsEntity, DistrictProvinceDTO.class);
    }

    @Override
    public DistrictDTO findWardsByDistrictId(String district_id, boolean province_include) {

        DistrictsEntity districtsEntity = (
                province_include
                        ?
                        this.districtRepository.findWardsByDistrictId(district_id)
                        :
                        this.districtRepository.findWardsExcludeProvinceByDistrictId(district_id)
                )
                .orElseThrow(
                    () -> new NotFoundException("Phường xã hiện không tồn tại!")
                );

        return modelMapper.map(districtsEntity,
                province_include
                        ?
                        DistrictProvinceWardsDTO.class
                        :
                        DistrictWardsDTO.class
                );
    }
}
