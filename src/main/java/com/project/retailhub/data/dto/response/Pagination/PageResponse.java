package com.project.retailhub.data.dto.response.Pagination;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PageResponse<T> {
    int currentPage;
    int pareSize;
    int totalPages;
    long totalElements;

    @Builder.Default
    List<T> data = Collections.emptyList();
}
