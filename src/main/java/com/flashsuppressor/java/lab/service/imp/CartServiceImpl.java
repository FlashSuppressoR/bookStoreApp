package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.dto.CartDTO;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
import com.flashsuppressor.java.lab.repository.CartRepository;
import com.flashsuppressor.java.lab.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public CartServiceImpl(@Qualifier("hibernateCartRepository")
                                     CartRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDTO findById(int id) throws ServiceException {
        CartDTO cartDTO = null;
        try{
            Cart cart = repository.findById(1);
            cartDTO = convertToCartDTO(cart);
        }
        catch (RepositoryException ex){
            throw new ServiceException(ex.getMessage());
        }
        return cartDTO;
    }

    @Override
    public List<CartDTO> findAll() throws ServiceException{
        List<CartDTO> cartDTOs = new ArrayList<>();
        List<Cart> carts = null;
        try {
            carts = repository.findAll();
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        if (carts.size() > 0) {
            cartDTOs = carts.stream().map(this::convertToCartDTO).collect(Collectors.toList());
        }
        return cartDTOs;
    }

    @Override
    public void create(Cart cart) throws ServiceException {
        try {
            repository.create(cart);
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public CartDTO update(Cart cart) throws ServiceException {
        CartDTO updatedCartDTO = null;
        try {
            Cart updatedCart = repository.update(cart);
            if (updatedCart != null) {
                updatedCartDTO = convertToCartDTO(updatedCart);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        return updatedCartDTO;
    }

    @Override
    public boolean deleteById(int id) throws ServiceException {
        boolean result;
        try {
            result = repository.deleteById(id);
        } catch (RepositoryException | SQLException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return result;
    }

    private CartDTO convertToCartDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }
}
