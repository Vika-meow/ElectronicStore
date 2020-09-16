package com.github.vika.store.repository;

import com.github.vika.store.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    public Iterable<Product> getByProductType_Id(int id);
}
