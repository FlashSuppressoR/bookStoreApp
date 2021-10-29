package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Cart;

import java.sql.SQLException;
import java.util.List;

public interface CartRepository {

    List<Cart> findAll() throws SQLException;
    Cart add(Cart cart) throws SQLException;
    Cart update(Cart cart) throws SQLException;
    Cart findById(int id) throws SQLException;
    boolean deleteById(int id) throws SQLException;
}
