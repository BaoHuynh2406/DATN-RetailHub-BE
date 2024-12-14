package com.project.retailhub.data.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse {

    private Long id;

    private double discountRate;

    private Date startDate;

    private Date endDate;

    private long productId;

    private String productName;

    private BigDecimal price;

    private String image;

    private boolean active;
}
