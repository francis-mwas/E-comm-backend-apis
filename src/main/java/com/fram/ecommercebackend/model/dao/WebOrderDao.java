package com.fram.ecommercebackend.model.dao;

import com.fram.ecommercebackend.model.WebOrder;
import org.springframework.data.repository.CrudRepository;

public interface WebOrderDao extends CrudRepository<WebOrder, Long> {
}
