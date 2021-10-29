package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.*;
import com.flashsuppressor.java.lab.repository.CartRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.flashsuppressor.java.lab.util.HibernateUtil.getSessionFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HibernateCartRepositoryTest {
    private final CartRepository cartRepository;
    private final List<Cart> expectedCarts;

    public HibernateCartRepositoryTest() {
        super();
        this.cartRepository = new HibernateCartRepository(getSessionFactory().openSession());
        expectedCarts = new ArrayList<>() {{
            add(new Cart( 1 , new Customer( 1 , "Max", "Max@com" , "max"), 1L, 1));
            add(new Cart( 2 , new Customer( 2 , "Alex", "Alex@com" , "alex"), 2L, 1));
            add(new Cart( 3 , new Customer(3,"Rus", "Rus@com" , "rus"), 3L,1 ));
        }};
    }

    @Test
    public void findAll() throws SQLException {
        //given
        //when
        List<Cart> actualCarts = cartRepository.findAll();
        //then
        for (int i = 0; i < expectedCarts.size(); i++) {
            assertCartEquals(expectedCarts.get(i), actualCarts.get(i));
        }
    }

    @Test
    public void findById() throws SQLException {
        //given
        Cart expectedCart = new Cart( 2 , new Customer( 2 , "Alex", "Alex@com" , "alex"), 2L, 1);
        //when
        Cart actualCart = cartRepository.findById(2);
        //then
        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void add() throws SQLException {
        //given
        Cart expectedCart = new Cart( 4 ,
                new Customer( 4 , "Max Offer", "MaxOf@com" , "maxee3"), 2L, 1);
        //when
        Cart actualCart = cartRepository.add(expectedCart);
        //then
        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void update() throws SQLException {
        //given
        Cart expectedCart = new Cart( 1 , new Customer( 1 , "Max", "Max@com" , "max"), 1L, 2);
        //when
        Cart actualCart = cartRepository.update(expectedCart);
        //then
        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void deleteById() throws SQLException {
        //when
        int cartId = 1;
        //then
        assertTrue(cartRepository.deleteById(cartId));
    }

    private void assertCartEquals(Cart expectedCart, Cart actualCart) {
        assertEquals(expectedCart.getId(), actualCart.getId());
        assertEquals(expectedCart.getCustomer(), actualCart.getCustomer());
        assertEquals(expectedCart.getBookId(), actualCart.getBookId());
        assertEquals(expectedCart.getBookCounter(), actualCart.getBookCounter());
    }
}