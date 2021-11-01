package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.CartRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class JDBCCartRepositoryTest extends BaseRepositoryTest {
    private final CartRepository cartRepository;
    private final List<Cart> expectedCarts;

    public JDBCCartRepositoryTest() {
        super();
        cartRepository = new JDBCCartRepository(getConnectionPool());
        expectedCarts = new ArrayList<>() {{
            add(new Cart(1, new Customer(1, "Max", "Max@com", "max"), 1L, 1));
            add(new Cart(2, new Customer(2, "Alex", "Alex@com", "alex"), 2L, 1));
            add(new Cart(3, new Customer(3, "Rus", "Rus@com", "rus"), 3L, 1));
        }};
    }

    @Test
    public void findAllTest() throws SQLException {
        //given
        //when
        List<Cart> actualCarts = cartRepository.findAll();
        //then
        for (int i = 0; i < expectedCarts.size(); i++) {
            assertCartEquals(expectedCarts.get(i), actualCarts.get(i));
        }
    }

    @Test
    public void addTest() throws SQLException {
        //given
        Cart expectedCart = new Cart(4,
                new Customer(4, "Max Offer", "MaxOf@com", "maxee3"), 2L, 1);
        //when
        Cart actualCart = cartRepository.add(expectedCart);
        //then
        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void updateTest() throws SQLException {
        //given
        Cart expectedCart = new Cart(1, new Customer(1, "Max", "Max@com", "max"), 1L, 2);
        //when
        Cart actualCart = cartRepository.update(expectedCart);
        //then
        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void findByIdTest() throws SQLException {
        //given
        Cart expectedCart = new Cart(2, new Customer(2, "Alex", "Alex@com", "alex"), 2L, 1);
        //when
        Cart actualCart = cartRepository.findById(2);
        //then
        assertCartEquals(expectedCart, actualCart);
    }

    @Test
    public void deleteByIdTest() throws SQLException {
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