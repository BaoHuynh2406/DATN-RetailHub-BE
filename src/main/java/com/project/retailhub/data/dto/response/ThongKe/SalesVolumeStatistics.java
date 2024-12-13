package com.project.retailhub.data.dto.response.ThongKe;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SalesVolumeStatistics {
    Long productId;
    String productName;
    int quantitySold;
}
