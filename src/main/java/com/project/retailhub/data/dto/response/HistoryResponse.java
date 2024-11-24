package com.project.retailhub.data.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryResponse {
    Long historyId;          // ID lịch sử
    Date transactionDate;    // Ngày giao dịch
    int points;              // Số điểm giao dịch
    String transactionType;  // Loại giao dịch ("Tích điểm", "Đổi điểm")
    String description;      // Mô tả giao dịch
    Long userId;             // ID người thực hiện giao dịch
    Long customerId;         // ID khách hàng
}
