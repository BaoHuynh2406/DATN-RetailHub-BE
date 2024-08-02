package com.project.retailhub.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "invalidate_token")
public class InvalidateToken {
    @Id
    String tokenId;
    Date expiryTime; //Thời gian mà token hết hạn //Dùng để quét và xóa đi những token hết hạn
}
