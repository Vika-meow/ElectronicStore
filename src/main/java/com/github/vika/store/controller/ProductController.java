package com.github.vika.store.controller;

import com.github.vika.store.domain.Product;
import com.github.vika.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/product/")
    public Iterable<Product> list(){
        return productRepository.findAll();
    }

    @PostMapping("/product/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product savedProduct = productRepository.save(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(location).body(savedProduct);
    }
}
