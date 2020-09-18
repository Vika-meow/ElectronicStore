package com.github.vika.store.controller;

import com.github.vika.store.domain.Manufacturer;
import com.github.vika.store.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ManufacturerController {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    /*All manufacturers*/
    @GetMapping("/manufacturer/")
    public Iterable<Manufacturer> list() {
        return manufacturerRepository.findAll();
    }

    /*Add new manufacturer*/
    @PostMapping("/manufacturer/")
    public ResponseEntity<Manufacturer> addManufacturer(@RequestBody Manufacturer manufacturer) {
        if(manufacturer.getName() == null)
            return ResponseEntity.badRequest().build();

        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(savedManufacturer.getId()).toUri();

        return ResponseEntity.created(location).body(savedManufacturer);
    }
}
