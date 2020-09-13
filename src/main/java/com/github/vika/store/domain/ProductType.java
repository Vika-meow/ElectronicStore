package com.github.vika.store.domain;

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
    private List<Product> products;
}
