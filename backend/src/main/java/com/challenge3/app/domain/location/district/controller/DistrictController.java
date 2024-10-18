package com.challenge3.app.domain.location.district.controller;

import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.common.response.IsFound;
import com.challenge3.app.domain.location.district.dto.DistrictDTO;
import com.challenge3.app.domain.location.district.dto.DistrictProvinceDTO;
import com.challenge3.app.domain.location.district.dto.DistrictWardsDTO;
import com.challenge3.app.domain.location.district.service.DistrictService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin({"http://localhost:8080", "http://localhost:4200"})
@ResponseBody
@RestController
@Tag(name = "Location District")
@RequestMapping("/location")
public class DistrictController {

    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/districts", method = RequestMethod.GET)
    public ApiResponse<Map<String, Object>> findAllDistrictsByProvinceId(
            @RequestParam(name = "province_id") String province_id,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "province include options",
                    schema = @Schema(
                            type = "boolean",
                            allowableValues = {"true", "false"},
                            defaultValue = "false"
                    )
            )
            @RequestParam(name = "province_include", defaultValue = "false") boolean province_include
    ) {

        List<DistrictDTO> districtsEntities = this.districtService.findAllDistrictsByProvinceId(province_id, province_include);

        String message = "Danh sách quận huyện hiện có!";

        Map<String, Object> response = new HashMap<>();
        response.put("districts", districtsEntities);

        return new IsFound<>(message, response);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/districts/{id}", method = RequestMethod.GET)
    public ApiResponse<Map<String, Object>> findDistrictById(
            @PathVariable(name = "id") String id
    ) {

        DistrictDTO districtProvinceDTO = this.districtService.findDistrictById(id);

        String message = "Quận huyện hiện ồn tại!";

        Map<String, Object> response = new HashMap<>();
        response.put("district", districtProvinceDTO);

        return new IsFound<>(message, response);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "districts/{id}/wards", method = RequestMethod.GET)
    public ApiResponse<Map<String, Object>> findWardsByDistrictId(
            @PathVariable(name = "id") String id,
            @RequestParam(value = "province_include", defaultValue = "false") boolean province_include
    ) {

//        System.out.println(code_name);
        DistrictDTO districtWardsDTO = this.districtService.findWardsByDistrictId(id, province_include);

        String message = "Danh sách phường xã hiện có!";

        Map<String, Object> response = new HashMap<>();
        response.put("district", districtWardsDTO);

        return new IsFound<>(message, response);
    }
}
