package com.challenge3.app.domain.user.response;

import com.challenge3.app.common.response.ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class SignInSuccessfully<T> extends ApiResponse<T> {

    public SignInSuccessfully(String message, T responseEntity) {
        super(true, HttpStatus.OK.value(), message, responseEntity);
    }
}
