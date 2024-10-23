package com.challenge3.app.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class ApiResponse<T> {

    @Builder.Default
    boolean status = false;

    int code;
    String message;

    // OffsetDateTime
    final long timestamp = ZonedDateTime.now().toEpochSecond();

    @Builder.Default
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data = null;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;

        injectResponseResult(data);
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


    void injectResponseResult(T data) {
        this.data = data;
    }
}
