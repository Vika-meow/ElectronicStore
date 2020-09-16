package com.github.vika.store.controller;

import com.github.vika.store.domain.Product;
import com.github.vika.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    /*Get all products*/
    @GetMapping("/product/")
    public Iterable<Product> list() {
        return productRepository.findAll();
    }

    /*Add new product*/
    @PostMapping("/product/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(location).body(savedProduct);
    }

    /*Get product by id*/
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> retrieveOrder(@PathVariable int id) {
        Optional<Product> product = productRepository.findById(id);

        if (!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.get());
    }

    /*Change existing product*/
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product newProduct, @PathVariable int id) {
        Optional<Product> stored = productRepository.findById(id);

        if (!stored.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product updated = stored.get();
        updated.setSerialNumber(newProduct.getSerialNumber());
        updated.setProductType(newProduct.getProductType());
        updated.setInfo(newProduct.getInfo());
        updated.setManufacturer(newProduct.getManufacturer());
        updated.setPrice(newProduct.getPrice());
        updated.setCount(newProduct.getCount());

        return ResponseEntity.ok(updated);
    }

    /*Get products with one type*/
    @GetMapping("/product/byType")
    public Iterable<Product> getProductsByType(@RequestParam int productType){
        return productRepository.getByProductType_Id(productType);
    }
}
