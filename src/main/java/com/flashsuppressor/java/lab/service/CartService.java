package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.dto.CartDTO;
import com.flashsuppressor.java.lab.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface CartService {

    CartDTO findById(int id) throws ServiceException;

    List<CartDTO> findAll() throws ServiceException;

    void create(Cart cart) throws ServiceException;

    CartDTO update(Cart cart) throws ServiceException;

    boolean deleteById(int id) throws ServiceException;
}
