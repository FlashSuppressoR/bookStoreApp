package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.dto.CartDTO;
import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;
import com.flashsuppressor.java.lab.repository.data.CartRepository;
import com.flashsuppressor.java.lab.service.CartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return convertToCartDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public List<CartDTO> findAll() {
        return repository.findAll().stream().map(this::convertToCartDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartDTO create(CartDTO cartDTO) {
        Cart newCart = repository.save(convertToCart(cartDTO));
        return convertToCartDTO(newCart);
    }

    @Override
    @Transactional
    public CartDTO update(CartDTO cartDTO) {
        CartDTO newCartDTO = null;
        try {
            Cart cart = repository.getById(cartDTO.getId());
            if (cartDTO.getCustomerDTO() != null) {
                cart.setCustomer(convertToCustomer(cartDTO.getCustomerDTO()));
            }
            cart.setBookId(cartDTO.getBookId());
            cart.setBookCounter(cartDTO.getBookCounter());
            newCartDTO = convertToCartDTO(cart);
        }
        catch (Exception e){
            System.out.println("Can't update cartDTO");
        }
        return newCartDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    private Customer convertToCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    private Cart convertToCart(CartDTO cartDTO) {
        return modelMapper.map(cartDTO, Cart.class);
    }

    private CartDTO convertToCartDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }
}
