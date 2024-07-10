package com.practice.product.util;

import com.practice.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductData {

    public static List<Product> getProductList() {
        List<Product> products = new ArrayList<>();
        Product product1 = Product.builder().id((long) 1).name("Marble cake").category("Cake").build();
        Product product2 = Product.builder().id((long) 2).name("Red Tomato").category("Fruits And Vegetables").build();
        Product product3 = Product.builder().id((long) 3).name("Orange Juice").category("Beverages").build();
        Product product4 = Product.builder().id((long) 4).name("Sanitizer").category("Health Care").build();
        Product product5 = Product.builder().id((long) 5).name("Vegetable Oil").category("Cooking").build();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        return products;
    }
}
