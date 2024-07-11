package com.practice.order.controller;

import com.practice.order.dto.ErrorResponseDto;
import com.practice.order.dto.OrderDto;
import com.practice.order.entity.Order;
import com.practice.order.entity.OrderStatus;
import com.practice.order.repository.OrderRepository;
import com.practice.order.request.PaymentRequest;
import com.practice.order.response.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private OrderDto orderDto;

    @GetMapping("/orders")
    public ResponseEntity<?> orders() {
        return new ResponseEntity<>(orderRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/orderById")
    public ResponseEntity<?> getOrderById(@RequestParam("id") Long id) {
        return new ResponseEntity<>(orderRepository.findById(id).get(),HttpStatus.OK);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        String paymentUrl = "http://localhost:8083/api/payment/makePayment";
        try {
            Long orderId = Math.round(Math.random()*1000000);
            order.setOrderId(orderId);
            PaymentRequest paymentRequest = PaymentRequest.builder().orderId(orderId).paymentMode(order.getPaymentMode()).isUPI(false).cardNo("48541855525")
                    .amount(108.50).build();
            ResponseEntity<?> result = restTemplateBuilder.build().postForEntity(paymentUrl, paymentRequest, PaymentResponse.class);
            PaymentResponse paymentResponse = (PaymentResponse) result.getBody();
            OrderStatus orderStatus = OrderStatus.builder().orderId(orderId).orderStatus(paymentResponse.getStatus()).customerId(order.getCustomerId()).build();
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
        } catch (Exception ex) {
            return new ResponseEntity<>("Order not created successfully",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<OrderDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderDto);
    }

}
