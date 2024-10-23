package com.challenge3.app.domain.user.response;

import com.challenge3.app.common.response.ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Data
public class GrantSuccessfully<T> extends ApiResponse<T> {

    public GrantSuccessfully(String message) {
        super(true, HttpStatus.OK.value(), message);
    }
}
