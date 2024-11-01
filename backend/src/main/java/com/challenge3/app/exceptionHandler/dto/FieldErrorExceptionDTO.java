package com.challenge3.app.exceptionHandler.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldErrorExceptionDTO {

    String field;
    String defaultMessage;
    
}
