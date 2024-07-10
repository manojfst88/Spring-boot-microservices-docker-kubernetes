package com.practice.customer.util;

import com.practice.customer.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerData {

    public static List<Customer> getCustomerList() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = Customer.builder().id((long) 1).name("Arpith Singh").address("Mumbai").mobile("98715XXXXX").build();
        Customer customer2 = Customer.builder().id((long) 2).name("Nirmal Kumar").address("Kolkata").mobile("84852XXXXX").build();
        Customer customer3 = Customer.builder().id((long) 3).name("Asha").address("Delhi").mobile("99826XXXXX").build();
        Customer customer4 = Customer.builder().id((long) 4).name("Preethi").address("Rajasthan").mobile("97842XXXXX").build();
        Customer customer5 = Customer.builder().id((long) 7).name("John Hammond").address("Kerala").mobile("99648XXXXX").build();

        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
        return customers;
    }
}
