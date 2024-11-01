package com.challenge3.app.domain.location.ward.controller;

import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.common.response.IsFound;
import com.challenge3.app.domain.location.ward.dto.WardDTO;
import com.challenge3.app.domain.location.ward.service.WardService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin({"http://localhost:8080", "http://localhost:4200"})
@RestController
@ResponseBody
@RequestMapping("/location")
@Tag(name = "Location ward")
public class WardController {

    private final WardService wardService;

    public WardController(WardService wardService) {
        this.wardService = wardService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/wards", method = RequestMethod.GET)
    public ApiResponse<List<WardDTO>> findALlWardByDistrictId(
            @RequestParam(name = "district_id") String district_id,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "district include options",
                    schema = @Schema(
                            type = "boolean",
                            allowableValues = {"true", "false"},
                            defaultValue = "false"
                    )
            )
            @RequestParam(name = "district_include", defaultValue = "false") boolean district_include
    ) {

        List<WardDTO> wardDTOs = this.wardService.findALlWardByDistrictId(district_id, district_include);

        String message = "Danh sách phường xã hiện có!";

        return new IsFound<>(message, wardDTOs);

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/wards/{id}", method = RequestMethod.GET)
    public ApiResponse<WardDTO> findWardDById(
            @PathVariable(name = "id") String id,
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

        WardDTO wardDTO = this.wardService.findWardDById(id, province_include);

        String message = "Phường xã hiện tồn tại!";

        return new IsFound<>(message, wardDTO);
    }
}
