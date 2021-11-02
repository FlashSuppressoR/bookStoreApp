package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Cart;

import java.sql.SQLException;
import java.util.List;

public interface CartRepository {

    Cart findById(int id) throws SQLException;

    List<Cart> findAll() throws SQLException;

    Cart create(Cart cart) throws SQLException;

    Cart update(Cart cart) throws SQLException;

    boolean deleteById(int id) throws SQLException;
}
