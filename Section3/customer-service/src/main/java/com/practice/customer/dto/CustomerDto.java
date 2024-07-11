package com.practice.customer.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "customer")
@Getter
@Setter
public class CustomerDto {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
