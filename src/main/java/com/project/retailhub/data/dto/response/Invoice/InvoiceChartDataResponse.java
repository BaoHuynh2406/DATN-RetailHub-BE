package com.project.retailhub.data.dto.response.Invoice;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class InvoiceChartDataResponse {
    Long invoiceId;
    Date invoiceDate;
    //Doanh thu
    BigDecimal finalTotal;
    //Tong thue
    BigDecimal totalTax;
    //Chi phi phai tra
    BigDecimal totalCost;
    //Giam gia
    BigDecimal discountAmount;

    String status;
}
