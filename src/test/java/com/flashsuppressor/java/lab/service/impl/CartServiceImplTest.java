package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.service.dto.BookDTO;
import com.flashsuppressor.java.lab.service.dto.CartDTO;
import com.flashsuppressor.java.lab.service.dto.CustomerDTO;
import com.flashsuppressor.java.lab.repository.data.CartRepository;
import com.flashsuppressor.java.lab.service.CartService;
import com.flashsuppressor.java.lab.service.dto.GenreDTO;
import com.flashsuppressor.java.lab.service.dto.PublisherDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CartServiceImplTest {

    @InjectMocks
    private CartService service;
    @Mock
    private CartRepository repository;
    @Mock
    private ModelMapper modelMapper;

    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Test
    void findByIdTest() {
        int cartID = 1;
        Cart cart = Cart.builder().id(4).customer
                (Customer.builder().id(4).name("Ae").email("Ae@gmail.com").password("Ad").build())
                .book(Book.builder().id(4L).name("New Book").price(123)
                        .publisher(Publisher.builder().id(4).name("Need For Speed").build())
                        .genre(Genre.builder().id(4).name("Soe Ew").build()).amount(1).build()).bookCounter(1).build();
        CartDTO expectedCartDTO = CartDTO.builder().id(4).customerDTO
                (CustomerDTO.builder().id(4).name("Ae").email("Ae@gmail.com").password("Ad").build())
                .bookDTO(BookDTO.builder().id(4).name("New Book").price(123)
                        .publisherDTO(PublisherDTO.builder().id(4).name("Need For Speed").build())
                        .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build()).bookCounter(1).build();

        when(repository.getById(cartID)).thenReturn(cart);
        when(modelMapper.map(cart, CartDTO.class)).thenReturn(expectedCartDTO);
        CartDTO actualCartDTO = service.findById(cartID);

        assertEquals(expectedCartDTO, actualCartDTO);
    }

    @Test
    void findAllTest() {
        int expectedSize = 2;
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Cart(), new Cart()));
        int actualSize = service.findAll(pageable).getSize();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createTest() {
        //given
        Cart cart = Cart.builder().id(4).customer
                (Customer.builder().id(4).name("Ae").email("Ae@gmail.com").password("Ad").build())
                .book(Book.builder().id(4L).name("New Book").price(123)
                        .publisher(Publisher.builder().id(4).name("Need For Speed").build())
                        .genre(Genre.builder().id(4).name("Soe Ew").build()).amount(1).build()).bookCounter(1).build();
        CartDTO cartDTO = CartDTO.builder().id(4).customerDTO
                (CustomerDTO.builder().id(4).name("Ae").email("Ae@gmail.com").password("Ad").build())
                .bookDTO(BookDTO.builder().id(4).name("New Book").price(123)
                        .publisherDTO(PublisherDTO.builder().id(4).name("Need For Speed").build())
                        .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build()).bookCounter(1).build();

        //when
        when(modelMapper.map(cartDTO, Cart.class)).thenReturn(cart);
        when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);
        when(repository.save(cart)).thenReturn(cart);
        CartDTO actualCartDTO = service.create(cartDTO);
        //then
        assertAll(() -> assertEquals(cartDTO.getId(), actualCartDTO.getId()),
                () -> assertEquals(cartDTO.getCustomerDTO(), actualCartDTO.getCustomerDTO()),
                () -> assertEquals(cartDTO.getBookDTO(), actualCartDTO.getBookDTO()),
                () -> assertEquals(cartDTO.getBookCounter(), actualCartDTO.getBookCounter()));
    }

    @Test
    void updateTest() {
        //given
        int cartId = 1;
        int bookCounter = 3;
        Cart cart = Cart.builder().id(4).customer
                (Customer.builder().id(4).name("Ae").email("Ae@gmail.com").password("Ad").build())
                .book(Book.builder().id(4L).name("New Book").price(123)
                        .publisher(Publisher.builder().id(4).name("Need For Speed").build())
                        .genre(Genre.builder().id(4).name("Soe Ew").build()).amount(1).build()).bookCounter(1).build();
        CartDTO cartDTO = CartDTO.builder().id(4).customerDTO
                (CustomerDTO.builder().id(4).name("Ae").email("Ae@gmail.com").password("Ad").build())
                .bookDTO(BookDTO.builder().id(4).name("New Book").price(123)
                        .publisherDTO(PublisherDTO.builder().id(4).name("Need For Speed").build())
                        .genreDTO(GenreDTO.builder().id(4).name("Soe Ew").build()).amount(1).build()).bookCounter(1).build();

        //when
        when(repository.getById(cartId)).thenReturn(cart);
        when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);
        when(repository.getById(cartId)).thenReturn(cart);
        CartDTO actualUpdatedCart = service.update(cartDTO);
        // then
        assertAll(() -> assertEquals(cartId, actualUpdatedCart.getId()),
                () -> assertEquals(bookCounter, actualUpdatedCart.getBookCounter())
        );
    }

    @Test
    void deleteByIdTest() {
        //given
        int validId = 1;
        //when
        repository.deleteById(validId);
        //then
        assertTrue(repository.findById(validId).isEmpty());
    }
}
