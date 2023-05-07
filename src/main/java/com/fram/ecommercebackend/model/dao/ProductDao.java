package com.fram.ecommercebackend.model.dao;

import com.fram.ecommercebackend.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Long> {
}
