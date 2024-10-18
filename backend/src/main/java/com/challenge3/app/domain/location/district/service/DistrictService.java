package com.challenge3.app.domain.location.district.service;

import com.challenge3.app.domain.location.district.dto.DistrictDTO;
import com.challenge3.app.domain.location.district.dto.DistrictProvinceDTO;
import com.challenge3.app.domain.location.district.dto.DistrictWardsDTO;

import java.util.List;

public interface DistrictService {

    List<DistrictDTO> findAllDistrictsByProvinceId(String province_code_name, boolean province_include);

    DistrictDTO findDistrictById(String code_name);

    DistrictDTO findWardsByDistrictId(String code_name, boolean province_include);
}
