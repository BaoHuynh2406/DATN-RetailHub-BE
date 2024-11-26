package com.project.retailhub.data.dto.response.BarcodeLookUp;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class InfoProduct {
    String productName;
    String imageUrl;
    Double price;
}
