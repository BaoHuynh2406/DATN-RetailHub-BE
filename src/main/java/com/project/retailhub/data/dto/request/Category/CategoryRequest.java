package com.project.retailhub.data.dto.request.Category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {
    int categoryId;
    String categoryName;
    Boolean isDelete;
}
