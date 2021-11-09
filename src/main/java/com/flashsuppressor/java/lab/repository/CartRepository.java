package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.exception.RepositoryException;

import java.sql.SQLException;
import java.util.List;

public interface CartRepository {

    Cart findById(int id) throws RepositoryException;

    List<Cart> findAll() throws RepositoryException;

    void create(Cart cart) throws RepositoryException;

    Cart update(Cart cart) throws RepositoryException;

    boolean deleteById(int id) throws RepositoryException, SQLException;
}
