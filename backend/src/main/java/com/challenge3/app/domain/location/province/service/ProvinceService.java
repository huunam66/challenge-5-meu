package com.challenge3.app.domain.location.province.service;


import com.challenge3.app.domain.location.province.dto.ProvinceDTO;
import com.challenge3.app.domain.location.province.dto.ProvinceDistrictsDTO;

import java.util.List;

public interface ProvinceService {

    List<ProvinceDTO> findAllProvinces();

    ProvinceDTO findByProvinceId(String code_name);

    ProvinceDTO findAllDistrictByProvinceId(String code_name);
}
