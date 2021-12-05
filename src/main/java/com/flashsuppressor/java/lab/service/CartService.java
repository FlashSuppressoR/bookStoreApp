package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.service.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {

    CartDTO findById(int id);

    Page<CartDTO> findAll(Pageable pageable);

    CartDTO create(CartDTO cartDTO);

    CartDTO update(CartDTO cartDTO);

    boolean deleteById(int id);
}
