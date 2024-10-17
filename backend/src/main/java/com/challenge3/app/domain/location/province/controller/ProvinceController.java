package com.challenge3.app.domain.location.province.controller;

import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.common.response.IsFound;
import com.challenge3.app.domain.location.province.dto.ProvinceDTO;
import com.challenge3.app.domain.location.province.dto.ProvinceDistrictsDTO;
import com.challenge3.app.domain.location.province.service.ProvinceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin({"http://localhost:8080", "http://localhost:4200"})
@ResponseBody
@RestController
@Tag(name = "Location Province")
@RequestMapping("/location")
public class ProvinceController {

    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @RequestMapping(path = "/provinces", method = RequestMethod.GET)
    public ApiResponse<Map<String, Object>> findAllProvinces() {

        List<ProvinceDTO> provinces = this.provinceService.findAllProvinces();

        String message = "Danh sách tỉnh thành hiện có!";

        Map<String, Object> response = new HashMap<>();
        response.put("provinces", provinces);

        return new IsFound<>(message, response);
    }

    @RequestMapping(path = "/provinces/{id}", method = RequestMethod.GET)
    public ApiResponse<Map<String, Object>> findProvinceById(@PathVariable("id") String id) {

        ProvinceDTO provinceDTO = this.provinceService.findByProvinceId(id);

        String message = "Tỉnh thành hiện tồn tại!";

        Map<String, Object> response = new HashMap<>();
        response.put("province", provinceDTO);

        return new IsFound<>(message, response);
    }

    @RequestMapping(path = "/provinces/{id}/districts", method = RequestMethod.GET)
    public ApiResponse<Map<String, Object>> findAndAllDistrictsByProvinceId(@PathVariable("id") String id) {
        ProvinceDTO provinceDTO = this.provinceService.findAllDistrictByProvinceId(id);

        String message = "Tỉnh thành và danh sách quận huyện liên quan hiện có!";

        Map<String, Object> response = new HashMap<>();
        response.put("province", provinceDTO);

        return new IsFound<>(message, response);
    }
}
