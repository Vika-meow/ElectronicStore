package com.github.vika.store.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Manufacturer {

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manufacturer",
            orphanRemoval = true, cascade = {CascadeType.REMOVE})
    private List<Product> productList;

    public Manufacturer() {
    }

    public Manufacturer(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Product> getProductList() {
        return productList;
    }
}
