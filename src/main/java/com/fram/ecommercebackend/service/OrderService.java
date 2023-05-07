package com.fram.ecommercebackend.service;

import com.fram.ecommercebackend.model.LocalUser;
import com.fram.ecommercebackend.model.WebOrder;
import com.fram.ecommercebackend.model.dao.WebOrderDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private WebOrderDao webOrderDao;

    public OrderService(WebOrderDao webOrderDao){
        this.webOrderDao = webOrderDao;
    }

    public List<WebOrder> getOrders(LocalUser user){
        return webOrderDao.findByUser(user);
    }
}
