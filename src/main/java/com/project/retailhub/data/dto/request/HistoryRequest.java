package com.project.retailhub.data.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryRequest {
    Long historyId;      // ID của lịch sử (có thể null khi tạo mới)
    Long customerId;     // ID khách hàng
    String action;       // Hành động: "tích điểm" hoặc "đổi điểm"
    int points;          // Điểm tích hoặc đổi
    LocalDateTime timestamp; // Thời điểm thực hiện (tuỳ chọn)
}
