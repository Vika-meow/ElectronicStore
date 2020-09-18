package com.github.vika.store.controller;

import com.github.vika.store.domain.ProductType;
import com.github.vika.store.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ProductTypeController {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @GetMapping("/productType/")
    public Iterable<ProductType> list() {
        return productTypeRepository.findAll();
    }

    @PostMapping("/productType/")
    public ResponseEntity<ProductType> addProductType(@RequestBody ProductType productType) {
        if(productType.getName() == null)
            return ResponseEntity.badRequest().build();

        ProductType savedProductType = productTypeRepository.save(productType);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProductType.getId()).toUri();
        return ResponseEntity.created(location).body(savedProductType);
    }
}
