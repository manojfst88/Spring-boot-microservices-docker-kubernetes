package com.practice.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    private Long id;
    private Long productId;
    private int count;
    private double totalPrice;
    private double gst;
    private double totalCost;
    private int customerId;
}
