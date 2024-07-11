package com.practice.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.product.entity.Product;
import com.practice.product.repository.CartRepository;
import com.practice.product.repository.ProductRepository;
import com.practice.product.request.Cart;
import com.practice.product.request.Orders;
import com.practice.product.util.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @GetMapping("/products")
    public ResponseEntity products() {
        return new ResponseEntity(productRepository.findAll(),HttpStatus.OK);
    }

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        try {
            productRepository.save(product);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody Cart cart) {
        try {
            com.practice.product.entity.Cart cartObj = com.practice.product.entity.Cart.builder().productId(cart.getProductId()).customerId(cart.getCustomerId()).count(cart.getCount()).build();
            cartRepository.save(cartObj);
        } catch (Exception e) {
            return new ResponseEntity("Add to cart issue",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/viewAllCart")
    public ResponseEntity<?> viewAllCart() {
        return new ResponseEntity(cartRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/viewCart")
    public ResponseEntity<?> viewCart(@RequestParam("customerId") Long customerId) {
        com.practice.product.entity.Cart cart;
        try {
          Optional<com.practice.product.entity.Cart> cartOptional = cartRepository.findByCustomerId(customerId);
          cart = !cartOptional.isEmpty() ? cartOptional.get() : new com.practice.product.entity.Cart();
        } catch (Exception e) {
            return new ResponseEntity("View cart issue",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(cart, HttpStatus.OK);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody Orders orders) {
        String orderUrl = "http://localhost:8082/api/order/createOrder";
        ResponseEntity<?> result = restTemplateBuilder.build().postForEntity(orderUrl, orders, String.class);
        return new ResponseEntity(result.getStatusCode());
    }
}
