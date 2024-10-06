package com.challenge3.app.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class IsFound extends ResponseAPI {

    public IsFound(String message, Object responseEntity) {
        super(true, HttpStatus.OK.value(), message, responseEntity);
    }
}
