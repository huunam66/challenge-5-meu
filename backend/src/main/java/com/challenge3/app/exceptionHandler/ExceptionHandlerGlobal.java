package com.challenge3.app.exceptionHandler;


import com.challenge3.app.exception.BadRequestException;
import com.challenge3.app.exception.IsNullOrEmptyException;
import com.challenge3.app.exception.NotFoundException;
import com.challenge3.app.common.response.ApiResponse;
import com.challenge3.app.exceptionHandler.dto.AuthenticationExceptionDTO;
import com.challenge3.app.exceptionHandler.dto.FieldErrorExceptionDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
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
import java.util.List;


@ResponseBody
@ControllerAdvice
public class ExceptionHandlerGlobal {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<List<FieldErrorExceptionDTO>> handleInvalidFields(MethodArgumentNotValidException ex) {

        BindingResult bindingResult = ex.getBindingResult();

        List<FieldError> fieldsErrors = bindingResult.getFieldErrors();

        List<FieldErrorExceptionDTO> fieldErrorExceptionDTOs = fieldsErrors.stream().map(
                fieldError -> FieldErrorExceptionDTO.builder()
                        .field(fieldError.getField())
                        .defaultMessage(fieldError.getDefaultMessage())
                        .build()
        ).toList();

        String message = "Phát hiện lỗi ràng buộc!";

        return new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                message,
                fieldErrorExceptionDTOs
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleRuntimeException(Exception e) {

        return new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ApiResponse<Object> handleHttpClientErrorException(HttpClientErrorException e) {

        return new ApiResponse<>(
                e.getStatusCode().value(),
                e.getMessage()
        );
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ApiResponse<Object> handleHttpServerErrorException(HttpServerErrorException e) {

        return new ApiResponse<>(
                e.getStatusCode().value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, IsNullOrEmptyException.class})
    public ApiResponse<Object> handleNotFoundIsNullOrEmpty(Exception e) {

        return new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiResponse<AuthenticationExceptionDTO> handleUsernameNotFoundException(UsernameNotFoundException e) {

        return new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(),
                AuthenticationExceptionDTO.builder().build()
        );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<AuthenticationExceptionDTO> handleBadCredentialsException(AuthenticationException ex) {

        return new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                AuthenticationExceptionDTO.builder().build()
        );
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<AuthenticationExceptionDTO> handleErrorCredentialsException(BadCredentialsException ignored) {

        String message = "Sai email hoặc mất khẩu!";

        return new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                message,
                AuthenticationExceptionDTO.builder().build()
        );
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiResponse<Object> handleBadSavedException(BadRequestException e) {

        return new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MalformedJwtException.class,
            SignatureException.class,
            NullPointerException.class,
            ExpiredJwtException.class
    })
    public ApiResponse<Object> handleInvalidJWT(Exception e) {

        return new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
    }
}
