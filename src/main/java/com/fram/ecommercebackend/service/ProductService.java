package com.fram.ecommercebackend.service;

import com.fram.ecommercebackend.model.Product;
import com.fram.ecommercebackend.model.dao.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao){
        this.productDao = productDao;
    }

    public List<Product> getProduct(){
        return (List<Product>) productDao.findAll();
    }
}
