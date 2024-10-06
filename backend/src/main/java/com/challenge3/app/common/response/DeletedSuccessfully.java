package com.challenge3.app.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class DeletedSuccessfully extends ResponseAPI {

    public DeletedSuccessfully(String message) {
        super(true, HttpStatus.OK.value(), message);
    }
}
