package com.challenge3.app.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class ResponseAPI {

    private boolean status = false;
    private int code;
    private String message;

    // OffsetDateTime
    private final ZonedDateTime timeAt = ZonedDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object responseEntity = null;

    public ResponseAPI(boolean status, int code, String message, Object responseEntity) {
        this.status = status;
        this.code = code;
        this.message = message;

        injectResponseEntity(responseEntity);
    }

    public ResponseAPI(int code, String message, Object responseEntity) {
        this.code = code;
        this.message = message;

        injectResponseEntity(responseEntity);
    }

    public ResponseAPI(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseAPI(boolean status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    private void injectResponseEntity(Object responseEntity) {
        this.responseEntity = responseEntity;
    }
}
