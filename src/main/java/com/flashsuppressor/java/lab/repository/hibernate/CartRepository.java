package com.flashsuppressor.java.lab.repository.hibernate;

import com.flashsuppressor.java.lab.entity.Cart;

import java.util.List;

@Deprecated
public interface CartRepository {

    Cart findById(int id);

    List<Cart> findAll();

    Cart create(Cart cart);

    Cart update(Cart cart);

    boolean deleteById(int id);
}
