package com.challenge3.app.domain.user.response;

import com.challenge3.app.common.response.ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class GrantSuccessfully<T> extends ApiResponse<T> {

    public GrantSuccessfully(String message) {
        super(true, HttpStatus.OK.value(), message);
    }
}
