package com.challenge3.app.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class SavedSuccessfully<T> extends ApiResponse<T> {

    public SavedSuccessfully(String message, T responseEntity) {
        super(true, HttpStatus.CREATED.value(), message, responseEntity);
    }
}
