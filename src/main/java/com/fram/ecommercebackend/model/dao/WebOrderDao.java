package com.fram.ecommercebackend.model.dao;

import com.fram.ecommercebackend.model.LocalUser;
import com.fram.ecommercebackend.model.WebOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WebOrderDao extends CrudRepository<WebOrder, Long> {
    List<WebOrder> findByUser(LocalUser user);
}

