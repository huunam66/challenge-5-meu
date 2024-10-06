package com.challenge3.app.domain.user.response;

import com.challenge3.app.common.response.ResponseAPI;
import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SignUpSuccessfully extends ResponseAPI {

    public SignUpSuccessfully(String message) {
        super(true, HttpStatus.CREATED.value(), message);
    }
}
