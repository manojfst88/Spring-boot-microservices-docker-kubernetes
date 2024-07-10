package com.practice.product.request;

import com.practice.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Long productId;
    private Long customerId;
    private int count;
}
