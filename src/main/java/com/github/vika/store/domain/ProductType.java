package com.github.vika.store.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productType",
            orphanRemoval = true, cascade = {CascadeType.REMOVE})
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Product> products;

    public ProductType() {
    }

    public ProductType(String name) {
        this.name = name;
    }

    /*@JsonIgnore
    public List<Product> getProducts() {
        return products;
    }*/
}
