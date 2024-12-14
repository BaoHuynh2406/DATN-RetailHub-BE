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
    private int month;        // Tháng
    private double doanhThu;  // Doanh thu
    private double loiNhuan;  // Lợi nhuận
}