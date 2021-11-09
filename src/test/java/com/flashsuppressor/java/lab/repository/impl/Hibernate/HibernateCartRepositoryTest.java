package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.*;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HibernateCartRepositoryTest extends BaseRepositoryTest {

    @Qualifier("hibernateCartRepository")
    @Autowired
    private CartRepository cartRepository;
    private final List<Cart> expectedCarts = new ArrayList<>() {{
        add(new Cart(1, new Customer(1, "Max", "Max@com", "max"), 1L, 1));
        add(new Cart(2, new Customer(2, "Alex", "Alex@com", "alex"), 2L, 1));
        add(new Cart(3, new Customer(3, "Rus", "Rus@com", "rus"), 3L, 1));
    }};

    @Test
    public void findAll() throws RepositoryException {
        List<Cart> actualCarts = cartRepository.findAll();

        for (int i = 0; i < expectedCarts.size(); i++) {
            assertCartEquals(expectedCarts.get(i), actualCarts.get(i));
        }
    }

    @Test
    public void findById() throws RepositoryException {
        Cart expectedCart = new Cart(2, new Customer(3, "Rus", "Rus@com", "rus"), 1L, 2);
        Cart actualCart = cartRepository.findById(2);

        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void createTest() throws RepositoryException {
        Cart expectedCart = new Cart(4,
                new Customer(4, "Max Offer", "MaxOf@com", "maxee3"), 2L, 1);

        cartRepository.create(expectedCart);

        assertEquals(4, cartRepository.findAll().size());
    }

    @Test
    public void update() throws RepositoryException {
        Cart expectedCart = new Cart(1, new Customer(1, "Max", "Max@com", "max"), 1L, 2);
        Cart actualCart = cartRepository.update(expectedCart);

        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void deleteById() throws RepositoryException {
        int cartId = 1;

        try {
            assertTrue(cartRepository.deleteById(cartId));
        } catch (RepositoryException | SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void assertCartEquals(Cart expectedCart, Cart actualCart) {
        assertEquals(expectedCart.getId(), actualCart.getId());
        assertEquals(expectedCart.getCustomer(), actualCart.getCustomer());
        assertEquals(expectedCart.getBookId(), actualCart.getBookId());
        assertEquals(expectedCart.getBookCounter(), actualCart.getBookCounter());
    }
}