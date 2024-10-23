package com.challenge3.app.domain.location.province.controller;

import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.common.response.IsFound;
import com.challenge3.app.domain.location.province.dto.ProvinceDTO;
import com.challenge3.app.domain.location.province.service.ProvinceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/provinces", method = RequestMethod.GET)
    public ApiResponse<List<ProvinceDTO>> findAllProvinces() {

        List<ProvinceDTO> provinces = this.provinceService.findAllProvinces();

        String message = "Danh sách tỉnh thành hiện có!";

        return new IsFound<>(message, provinces);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/provinces/{id}", method = RequestMethod.GET)
    public ApiResponse<ProvinceDTO> findProvinceById(@PathVariable("id") String id) {

        ProvinceDTO provinceDTO = this.provinceService.findByProvinceId(id);

        String message = "Tỉnh thành hiện tồn tại!";

        return new IsFound<>(message, provinceDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/provinces/{id}/districts", method = RequestMethod.GET)
    public ApiResponse<ProvinceDTO> findAndAllDistrictsByProvinceId(@PathVariable("id") String id) {

        ProvinceDTO provinceDTO = this.provinceService.findAllDistrictByProvinceId(id);

        String message = "Tỉnh thành và danh sách quận huyện liên quan hiện có!";

        return new IsFound<>(message, provinceDTO);
    }
}
