package com.prospecta.com.service;



import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.prospecta.com.Product;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class ProductService {
    private final String BASE_URL = "https://fakestoreapi.com/products";

    // Fetch products by category
    public List<Product> getProductsByCategory(String category) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "/category/" + category;
        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
        return List.of(response.getBody());
    }

    // Add a new product
    public Product addProduct(Product product) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> response = restTemplate.postForEntity(BASE_URL, product, Product.class);
        return response.getBody();
    }
}
