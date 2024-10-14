package com.challenge3.app.exceptionHandlerGlobal;


import com.challenge3.app.exception.BadRequestException;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import com.challenge3.app.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ResponseBody
@ControllerAdvice
public class ExceptionHandlerGlobal {

    // Bắt ngoại lệ khi các fields của một đối tượng không thỏa mãn Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleInvalidFields(MethodArgumentNotValidException ex) {

        BindingResult bindingResult = ex.getBindingResult();

        List<FieldError> fieldsErrors = bindingResult.getFieldErrors();

        Map<String, String> errors = new HashMap<>();
        fieldsErrors.forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        String message = "Phát hiện lỗi ràng buộc!";

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("invalidFields", errors);

        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), message, responseEntity);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleRuntimeException(Exception e) {
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ApiResponse handleHttpClientErrorException(HttpClientErrorException e) {
        return new ApiResponse(e.getStatusCode().value(), e.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ApiResponse handleHttpServerErrorException(HttpServerErrorException e) {
        return new ApiResponse(e.getStatusCode().value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, IsNullOrEmptyException.class})
    public ApiResponse handleNotFoundIsNullOrEmpty(Exception e) {
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiResponse handleUsernameNotFoundException(Exception e) {

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("authentication", false);

        return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), responseEntity);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ApiResponse handleBadCredentialsException(Exception ignored) {

        String message = "Sai email hoặc mất khẩu!";

        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("authentication", false);

        return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), message, responseEntity);
    }


    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ExceptionHandler(BadRequestException.class)
    public ApiResponse handleBadSavedException(BadRequestException e) {
        return new ApiResponse(HttpStatus.NOT_MODIFIED.value(), e.getMessage());
    }

}
