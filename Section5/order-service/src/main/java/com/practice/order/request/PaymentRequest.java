package com.practice.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private Long id;
    private Long orderId;
    private Long transactionId;
    private String paymentMode;
    private boolean isUPI;
    private String upiId;
    private String cardNo;
    private double amount;
    private String status;
}
