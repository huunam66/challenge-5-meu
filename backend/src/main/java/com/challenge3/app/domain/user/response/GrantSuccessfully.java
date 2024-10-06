package com.challenge3.app.domain.user.response;

import com.challenge3.app.common.response.ResponseAPI;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class GrantSuccessfully extends ResponseAPI {

    public GrantSuccessfully(String message) {
        super(true, HttpStatus.OK.value(), message);
    }
}
