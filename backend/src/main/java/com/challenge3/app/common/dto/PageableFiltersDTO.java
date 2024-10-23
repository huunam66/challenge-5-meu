package com.challenge3.app.common.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageableFiltersDTO<T> {

    int pageNumber;
    int totalPages;
    long offset;
    long totalElements;
    boolean hasPreviousPage;
    boolean hasNextPage;
    T content;
}
