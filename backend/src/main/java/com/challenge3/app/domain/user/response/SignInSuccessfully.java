package com.challenge3.app.domain.user.response;

import com.challenge3.app.common.response.ResponseAPI;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SignInSuccessfully extends ResponseAPI {

    public SignInSuccessfully(String message, Object responseEntity) {
        super(true, HttpStatus.OK.value(), message, responseEntity);
    }
}
