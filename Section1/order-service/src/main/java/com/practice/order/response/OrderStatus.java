package com.practice.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatus {

    private String orderId;
    private String transactionId;
    private String status;
    private double transactedAmount;
    private String paymentMode;
    private String cardNo;
    private String upiId;
    private boolean isUPI;
}
