package com.project.retailhub.data.dto.response.ThongKe;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RevenueProfitResponse {
    private int month;
    private double revenue;  // Thay "doanhThu" thành "revenue"
    private double profit;   // Thay "loiNhuan" thành "profit"
}