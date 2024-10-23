package com.challenge3.app.domain.user.response;

import com.challenge3.app.common.response.ApiResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class SignUpSuccessfully<T> extends ApiResponse<T> {

    public SignUpSuccessfully(String message) {
        super(true, HttpStatus.CREATED.value(), message);
    }
}
