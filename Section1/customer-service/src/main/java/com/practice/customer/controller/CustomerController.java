package com.practice.customer.controller;

import com.practice.customer.entity.Customer;
import com.practice.customer.repository.CustomerRepository;
import com.practice.customer.util.CustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customersList")
    public ResponseEntity<?> getCustomersList() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getCustomerByID")
    public ResponseEntity<?> getCustomerByID(@RequestParam("id") Long id) {
       Optional<Customer> customerData = customerRepository.findById(id);
        if(customerData.isEmpty()) {
            return new ResponseEntity<>("No customer found!!!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            customerRepository.save(customer);
        } catch (Exception ex) {
            return new ResponseEntity<>("Customer not saved successfully", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Customer saved successfully", HttpStatus.OK);
    }
}
