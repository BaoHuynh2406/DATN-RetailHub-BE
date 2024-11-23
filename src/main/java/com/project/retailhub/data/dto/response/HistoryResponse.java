package com.project.retailhub.data.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryResponse {
    Long historyId;       // ID của lịch sử
    Long customerId;      // ID khách hàng
    String fullName;      // Tên khách hàng
    String phoneNumber;   // Số điện thoại khách hàng
    String action;        // Hành động: "tích điểm" hoặc "đổi điểm"
    int points;           // Điểm tích hoặc đổi
    LocalDateTime timestamp; // Thời điểm thực hiện
}
