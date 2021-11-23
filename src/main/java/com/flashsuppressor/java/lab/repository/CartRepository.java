package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Cart;

import java.util.List;

public interface CartRepository {

    Cart findById(int id);

    List<Cart> findAll();

    Cart create(Cart cart);

    Cart update(Cart cart);

    boolean deleteById(int id);
}
