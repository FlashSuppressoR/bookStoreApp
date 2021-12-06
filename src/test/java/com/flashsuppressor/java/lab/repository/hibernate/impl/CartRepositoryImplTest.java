package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.entity.Role;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.hibernate.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class CartRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    private final List<Cart> expectedCarts = new ArrayList<>() {{
        add(new Cart(1, new Customer(1, "Max", "Max@com", "max", Role.USER), new Book(1L, "Little Bee", 3.22,
                new Publisher(1, "Big Daddy"), new Genre(1, "Fantasy"), 0), 1));
        add(new Cart(2, new Customer(2, "Alex", "Alex@com", "alex", Role.ADMIN), new Book(2L, "Big system Black Sun", 2.33,
                new Publisher(2, "Minsk prod"), new Genre(1, "Fantasy"), 0), 1));
        add(new Cart(3, new Customer(3, "Rus", "Rus@com", "rus", Role.USER), new Book(3L, "Alex Green", 13.22,
                new Publisher(1, "New Town"), new Genre(3, "Humor"), 0), 1));
    }};

    @Test
    public void findAllTest() {
        List<Cart> actualCarts = cartRepository.findAll();

        for (int i = 0; i < expectedCarts.size(); i++) {
            assertCartEquals(expectedCarts.get(i), actualCarts.get(i));
        }
    }

    @Test
    public void findByIdTest() {
        Cart expectedCart = new Cart(2, new Customer(3, "Rus", "Rus@com", "rus", Role.USER), new Book(1L, "Little Bee", 3.22,
                new Publisher(1, "Big Daddy"), new Genre(1, "Fantasy"), 0), 1);
        Cart actualCart = cartRepository.findById(2);

        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void createTest() {
        Cart expectedCart = new Cart(4,
                new Customer(4, "Max Offer", "MaxOf@com", "maxee3", Role.USER), new Book(4L, "Green T", 3.22,
                new Publisher(1, "Big Daddy"), new Genre(1, "Fantasy"), 0), 1);

        cartRepository.create(expectedCart);

        assertEquals(4, cartRepository.findAll().size());
    }

    @Test
    public void updateTest() {
        Cart expectedCart = new Cart(1, new Customer(1, "Max", "Max@com", "max", Role.USER), new Book(1L, "Little Bee", 3.22,
                new Publisher(1, "Big Daddy"), new Genre(1, "Fantasy"), 0), 2);
        Cart actualCart = cartRepository.update(expectedCart);

        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void deleteByIdTest() {
        int cartId = 1;

        assertTrue(cartRepository.deleteById(cartId));
    }

    private void assertCartEquals(Cart expectedCart, Cart actualCart) {
        assertEquals(expectedCart.getId(), actualCart.getId());
        assertEquals(expectedCart.getCustomer(), actualCart.getCustomer());
        assertEquals(expectedCart.getBook(), actualCart.getBook());
        assertEquals(expectedCart.getBookCounter(), actualCart.getBookCounter());
    }
}