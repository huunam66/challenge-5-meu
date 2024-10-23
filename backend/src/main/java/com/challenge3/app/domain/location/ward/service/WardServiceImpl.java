package com.challenge3.app.domain.location.ward.service;

import com.challenge3.app.domain.location.ward.dto.WardDTO;
import com.challenge3.app.domain.location.ward.dto.WardDistrictDTO;
import com.challenge3.app.domain.location.ward.dto.WardDistrictProvinceDTO;
import com.challenge3.app.domain.location.ward.repository.WardRepository;
import com.challenge3.app.entity.WardEntity;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
@Service
public class WardServiceImpl implements WardService{

    private final WardRepository wardRepository;

    private final ModelMapper modelMapper;

    public WardServiceImpl(WardRepository wardRepository, ModelMapper modelMapper) {
        this.wardRepository = wardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<WardDTO> findALlWardByDistrictId(String district_id, boolean district_include) {

//        System.out.println("district_include: " + district_include);

        List<WardEntity> wardEntities = this.wardRepository.findALlWardByDistrictId(district_id);

        return this.modelMapper.map(wardEntities,
                !district_include
                        ?
                        new TypeToken<List<WardDTO>>(){}.getType()
                        :
                        new TypeToken<List<WardDistrictDTO>>(){}.getType()
                );
    }

    @Override
    public WardDTO findWardDById(String id, boolean province_include) {

        WardEntity wardEntity = (
                !province_include
                        ?
                        this.wardRepository.findWardDById(id)
                        :
                        this.wardRepository.findWardDistrictProvinceByWardId(id)
        ).orElseThrow(
                () -> new NotFoundException("Phường xã hiện không tồn tại!")
        );

        return this.modelMapper.map(wardEntity,
                !province_include
                        ?
                        WardDistrictDTO.class
                        :
                        WardDistrictProvinceDTO.class
                );
    }

}
