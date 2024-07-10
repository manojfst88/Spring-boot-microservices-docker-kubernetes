package com.practice.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long productId;
    private int count;
    private double totalPrice;
    private double gst;
    private double totalCost;
    private Long customerId;
    private String paymentMode;
    @OneToOne(
            cascade = CascadeType.ALL)
    private OrderStatus orderStatus;
}
