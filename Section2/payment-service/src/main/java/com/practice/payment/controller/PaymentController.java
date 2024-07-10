package com.practice.payment.controller;

import com.practice.payment.entity.Payment;
import com.practice.payment.repository.PaymentRepository;
import com.practice.payment.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping("/viewPayments")
    public ResponseEntity<?> viewPayments() {
        return new ResponseEntity<>(paymentRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/makePayment")
    public ResponseEntity<?> makePayment(@RequestBody Payment payment) {
        PaymentResponse paymentResponse = new PaymentResponse();
        try {
            Long transactionId = Math.round(Math.random() * 1000000);
            payment.setTransactionId(transactionId);
            paymentRepository.save(payment);
            paymentResponse = PaymentResponse.builder().transactionId(transactionId).status("Payment success").transactionAmount(payment.getAmount()).build();
        } catch (Exception ex) {
            paymentResponse.setTransactionId(payment.getTransactionId());
            paymentResponse.setTransactionAmount(payment.getAmount());
            paymentResponse.setStatus("Transaction Failed!!!");
            return new ResponseEntity<>(paymentResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/checkPaymentStatus")
    public ResponseEntity<?> checkPaymentStatus(@RequestParam("transactionId") Long transactionId) {
        Optional<Payment> payment = paymentRepository.findByTransactionId(transactionId);
        if(payment.isEmpty()) {
            return new ResponseEntity<>("No Payment details found!!!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
