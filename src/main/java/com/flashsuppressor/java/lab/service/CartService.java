package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.dto.CartDTO;

import java.util.List;

public interface CartService {

    CartDTO findById(int id);

    List<CartDTO> findAll();

    CartDTO create(CartDTO cartDTO);

    CartDTO update(CartDTO cartDTO);

    boolean deleteById(int id);
}
