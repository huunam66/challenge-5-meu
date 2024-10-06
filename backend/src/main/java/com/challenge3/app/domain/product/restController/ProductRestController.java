package com.challenge3.app.domain.product.restController;

import com.challenge3.app.common.response.*;
import com.challenge3.app.domain.product.response.ProductFilters;
import com.challenge3.app.domain.product.request.ProductRequest;
import com.challenge3.app.domain.product.service.ProductService;
import com.challenge3.app.entity.ProductEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@CrossOrigin
@RestController
@ResponseBody
@RequestMapping(path = "/api/products")
@Tag(name = "Products")
public class ProductRestController {

    private final ProductService productService;


    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseAPI getProducts() {

        List<ProductEntity> productList = productService.findAll();

        String message = "Danh sách sản phẩm hiện có!";

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("products", productList);

        return new IsFound(message, responseEntity);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{code}", method = RequestMethod.GET)
    public ResponseAPI getSingleProduct(@PathVariable(name = "code") String code) {

        ProductEntity product = productService.findByCode(code);

        String message = "Sản phâm có mã " + code + " tồn tại!";

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("product", product);

        return new IsFound(message, responseEntity);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseAPI saveProduct(@RequestBody @Valid ProductRequest productRequest) {

        System.out.println(productRequest);
        ProductEntity productSaved = productService.save(productRequest);

        String message = "Lưu sản phẩm thành công!";

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("product", productSaved);

        return new SavedSuccessfully(message, responseEntity);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/{code}", method = RequestMethod.PUT)
    public ResponseAPI modifyProduct(@RequestBody @Valid ProductRequest productDTO,
                                     @PathVariable(name = "code") String code) {

        ProductEntity productModified = productService.save(productDTO, code);

        String message = "Chỉnh sửa sản phẩm thành công!";

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("product", productModified);

        return new SavedSuccessfully(message, responseEntity);
    }

    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseAPI productFilters(@RequestParam(name = "limit") int limit, @RequestParam(name = "page") int page) {

        ProductFilters productFilters = this.productService.productFilters(limit, page);

        String message = "Danh sách sản phẩm tại trang " + page + " hiện có!";

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("results", productFilters);

        return new IsFound(message, responseEntity);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/search/{key}", method = RequestMethod.GET)
    public ResponseAPI searchProduct(@PathVariable(name = "key") String key) {

        List<ProductEntity> productList = this.productService.search(key);

        String messgage = "Tìm kiếm sản phẩm thành công!";

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("products", productList);

        return new IsFound(messgage, responseEntity);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{code}", method = RequestMethod.DELETE)
    public ResponseAPI deleteProduct(@PathVariable(name = "code") String code) {

        String nameOldProduct = this.productService.delete(code);

        String message = "Đã xóa sản phẩm " + nameOldProduct + " thành công!";

        return new DeletedSuccessfully(message);
    }
}
