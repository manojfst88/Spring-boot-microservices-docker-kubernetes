package com.practice.customer.controller;

import com.practice.customer.dto.CustomerDto;
import com.practice.customer.dto.ErrorResponseDto;
import com.practice.customer.entity.Customer;
import com.practice.customer.repository.CustomerRepository;
import com.practice.customer.util.CustomerData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CustomerDto customerDto;

    @GetMapping("/buildVersion")
    public ResponseEntity<?> getBuildVersion() {
        return new ResponseEntity<>(buildVersion, HttpStatus.OK);
    }

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
    public ResponseEntity<CustomerDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }
}
