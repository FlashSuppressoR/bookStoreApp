package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.repository.CartRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class HibernateCartRepository implements CartRepository {
    private final Session session;
    private static final String FIND_CARTS_QUERY = "select c from Cart c ";

    public HibernateCartRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Cart> findAll() throws SQLException {
        List<Cart> cartList;
        try {
            cartList = session.createQuery(FIND_CARTS_QUERY, Cart.class).list();
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return cartList;
    }

    @Override
    public Cart findById(int id) throws SQLException {
        Cart cart;
        try {
            cart = session.find(Cart.class, id);
        } catch (Exception ex) {
            throw new SQLException("Can not find Cart", ex);
        }
        return cart;
    }

    @Override
    public Cart add(Cart cart) throws SQLException {
        Cart newCart;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer newBookId = (Integer) session.save("Cart", cart);
            newCart = session.find(Cart.class, newBookId);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the Cart", ex);
        }
        return newCart;
    }

    @Override
    public Cart update(Cart cart) throws SQLException {
        Cart updatedCart;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update("Cart", cart);
            updatedCart = session.find(Cart.class, cart.getId());
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return updatedCart;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Cart cart = session.find(Cart.class, id);

            if (cart != null) {
                session.delete(cart);
                result = (null == session.find(Cart.class, id));
            } else {
                result = false;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Can not remove 'Cart'", ex);
        }
        return result;
    }
}
