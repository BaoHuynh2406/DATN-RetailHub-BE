package com.project.retailhub.data.dto.response.Pagination;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PageResponse {
    int page;
    int size;
    int totalpage;
    long totalElement;
}
