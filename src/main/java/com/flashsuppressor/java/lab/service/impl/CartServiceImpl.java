package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.data.CartRepository;
import com.flashsuppressor.java.lab.service.BookService;
import com.flashsuppressor.java.lab.service.CartService;
import com.flashsuppressor.java.lab.service.CustomerService;
import com.flashsuppressor.java.lab.service.dto.BookDTO;
import com.flashsuppressor.java.lab.service.dto.CartDTO;
import com.flashsuppressor.java.lab.service.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CustomerService customerService;
    private final BookService bookService;
    private final CartRepository repository;
    private final ModelMapper modelMapper;
    private final Pageable pageable = PageRequest.of(0, 5, Sort.by("name"));

    @Override
    public CartDTO findById(int id) {
        return convertToCartDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<CartDTO> findAll(Pageable pgb) {
        Page<Cart> pages = repository.findAll(pageable);

        return new PageImpl<>(pages.stream().map(this::convertToCartDTO).collect(Collectors.toList()));
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
            cart.setCustomer(convertToCustomer(customerService.findById(cartDTO.getCustomerId())));
            cart.setBook(convertToBook(bookService.findById(cartDTO.getBookId())));
            cart.setBookCounter(cartDTO.getBookCounter());

            repository.flush();
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
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    private Book convertToBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
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
