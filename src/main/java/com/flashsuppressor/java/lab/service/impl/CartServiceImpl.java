package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.dto.CartDTO;
import com.flashsuppressor.java.lab.repository.CartRepository;
import com.flashsuppressor.java.lab.service.CartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly=true)
    public CartDTO findById(int id) {
        Cart cart = repository.findById(id);

        return convertToCartDTO(cart);
    }

    @Override
    @Transactional(readOnly=true)
    public List<CartDTO> findAll() {
        List<CartDTO> cartDTOs = new ArrayList<>();
        List<Cart> carts = repository.findAll();
        if (carts.size() > 0) {
            cartDTOs = carts.stream().map(this::convertToCartDTO).collect(Collectors.toList());
        }

        return cartDTOs;
    }

    @Override
    @Transactional
    public void create(Cart cart) {
        repository.create(cart);
    }

    @Override
    @Transactional
    public CartDTO update(Cart cart) {
        CartDTO updatedCartDTO = null;
        Cart updatedCart = repository.update(cart);
        if (updatedCart != null) {
            updatedCartDTO = convertToCartDTO(updatedCart);
        }

        return updatedCartDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {

        return repository.deleteById(id);
    }

    private CartDTO convertToCartDTO(Cart cart) {

        return modelMapper.map(cart, CartDTO.class);
    }
}
