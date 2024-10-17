package com.challenge3.app.domain.location.ward.service;

import com.challenge3.app.domain.location.ward.dto.WardDTO;
import com.challenge3.app.entity.WardEntity;

import java.util.List;
import java.util.Optional;

public interface WardService {

    List<WardDTO> findALlWardByDistrictId(String district_id, boolean district_include);

    WardDTO findWardDById(String id, boolean province_include);

}
