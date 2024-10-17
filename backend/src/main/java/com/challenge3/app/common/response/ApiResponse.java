package com.challenge3.app.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class ApiResponse<T> {

    private boolean status = false;
    private int code;
    private String message;

    // OffsetDateTime
    private final ZonedDateTime timestamp = ZonedDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data = null;

    public ApiResponse(boolean status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;

        injectResponseEntity(data);
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;

        injectResponseEntity(data);
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(boolean status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    private void injectResponseEntity(T data) {
        this.data = data;
    }
}
