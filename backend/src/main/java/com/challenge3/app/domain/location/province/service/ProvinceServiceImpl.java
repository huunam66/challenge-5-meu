package com.challenge3.app.domain.location.province.service;

import com.challenge3.app.domain.location.province.dto.ProvinceDTO;
import com.challenge3.app.domain.location.province.dto.ProvinceDistrictsDTO;
import com.challenge3.app.domain.location.province.repository.ProvinceRepository;
import com.challenge3.app.entity.ProvinceEntity;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import java.util.List;


@Service
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    private final ModelMapper modelMapper;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository, ModelMapper modelMapper) {
        this.provinceRepository = provinceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProvinceDTO> findAllProvinces() {

        List<ProvinceEntity> provinces = this.provinceRepository.findAllProvinces();

        Type listType = new TypeToken<List<ProvinceDTO>>() {}.getType();

        return this.modelMapper.map(provinces, listType);
    }

    @Override
    public ProvinceDTO findByProvinceId(String province_id) {
        ProvinceEntity provinceEntity = this.provinceRepository.findByProvinceId(province_id).orElseThrow(
                () -> new NotFoundException("Tỉnh thành hiện không tồn tại!")
        );

        return this.modelMapper.map(provinceEntity, ProvinceDTO.class);
    }

    @Override
    public ProvinceDTO findAllDistrictByProvinceId(String province_id) {

        ProvinceEntity provinceEntity =  this.provinceRepository.findAllDistrictByProvinceId(province_id).orElseThrow(
                () -> new NotFoundException("Tỉnh thành hiện không tồn tại!")
        );

        return this.modelMapper.map(provinceEntity, ProvinceDistrictsDTO.class);
    }
}
