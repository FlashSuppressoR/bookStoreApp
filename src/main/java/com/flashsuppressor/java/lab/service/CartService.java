package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.dto.CartDTO;

import java.util.List;

public interface CartService {

    CartDTO findById(int id);

    List<CartDTO> findAll();

    void create(Cart cart);

    CartDTO update(Cart cart);

    boolean deleteById(int id);
}
