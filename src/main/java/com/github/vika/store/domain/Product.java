package com.github.vika.store.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String serialNumber;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id")
    @JsonManagedReference
    private ProductType productType;

    @Getter
    @Setter
    private String info;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Getter
    @Setter
    private int price;

    @Getter
    @Setter
    private int count;

    public Product() {
    }

    public Product(String serialNumber, ProductType productType, String info,
                   Manufacturer manufacturer, int price, int count) {
        this.serialNumber = serialNumber;
        this.productType = productType;
        this.info = info;
        this.manufacturer = manufacturer;
        this.price = price;
        this.count = count;
    }
}
