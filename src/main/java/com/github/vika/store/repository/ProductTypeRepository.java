package com.github.vika.store.repository;

import com.github.vika.store.domain.ProductType;
import org.springframework.data.repository.CrudRepository;

public interface ProductTypeRepository extends CrudRepository<ProductType, Integer> {
}
