package com.challenge3.app.domain.product.controller;

import com.challenge3.app.common.response.*;
import com.challenge3.app.domain.product.dto.ProductDTO;
import com.challenge3.app.domain.product.request.ProductRequest;
import com.challenge3.app.domain.product.service.ProductService;
import com.challenge3.app.common.dto.PageableFiltersDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin({"http://localhost:8080", "http://localhost:4200"})
@RestController
@ResponseBody
@RequestMapping(path = "/products")
@Tag(name = "Products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse<List<ProductDTO>> getProducts() {

        List<ProductDTO> productList = productService.findAll();

        String message = "Danh sách sản phẩm hiện có!";

        return new IsFound<>(message, productList);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{code}", method = RequestMethod.GET)
    public ApiResponse<ProductDTO> getSingleProduct(@PathVariable(name = "code") String code) {

        ProductDTO product = productService.findByCode(code);

        String message = "Sản phâm có mã " + code + " tồn tại!";

        return new IsFound<>(message, product);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse<ProductDTO> saveProduct(@RequestBody @Valid ProductRequest productRequest) {

//        System.out.println(productRequest);

        ProductDTO productSaved = productService.save(productRequest);

        String message = "Lưu sản phẩm thành công!";

        return new SavedSuccessfully<>(message, productSaved);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/{code}", method = RequestMethod.PUT)
    public ApiResponse<ProductDTO> modifyProduct(@RequestBody @Valid ProductRequest productDTO,
                                     @PathVariable(name = "code") String code) {

        ProductDTO productModified = productService.save(productDTO, code);

        String message = "Chỉnh sửa sản phẩm thành công!";

        return new SavedSuccessfully<>(message, productModified);
    }

    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageableFiltersDTO<List<ProductDTO>>> productFilters(
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "page") int page
    ) {

        PageableFiltersDTO<List<ProductDTO>> productFilters = this.productService.productFilters(limit, page);

        String message = "Danh sách sản phẩm tại trang " + page + " hiện có!";

        return new IsFound<>(message, productFilters);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/search/{key}", method = RequestMethod.GET)
    public ApiResponse<List<ProductDTO>> searchProduct(@PathVariable(name = "key") String key) {

        List<ProductDTO> productList = this.productService.search(key);

        String messgage = "Tìm kiếm sản phẩm thành công!";

        return new IsFound<>(messgage, productList);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{code}", method = RequestMethod.DELETE)
    public ApiResponse<Object> deleteProduct(@PathVariable(name = "code") String code) {

        String nameOldProduct = this.productService.delete(code);

        String message = "Đã xóa sản phẩm " + nameOldProduct + " thành công!";

        return new DeletedSuccessfully<>(message);
    }
}
