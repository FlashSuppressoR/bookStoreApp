package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.repository.CartRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateCartRepository implements CartRepository {
    private final Session session;
    private static final String FIND_CARTS_QUERY = "select c from Cart c ";

    public HibernateCartRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Cart> findAll() {

        return session.createQuery(FIND_CARTS_QUERY, Cart.class).list();
    }

    @Override
    public Cart findById(int id) {

        return session.find(Cart.class, id);
    }

    @Override
    public Cart create(Cart cart) {
        Cart newCart;
        Transaction transaction = session.beginTransaction();
        Integer newBookId = (Integer) session.save(cart);
        newCart = session.find(Cart.class, newBookId);
        transaction.commit();

        return newCart;
    }

    @Override
    public Cart update(Cart cart) {
        Cart updatedCart;
        Transaction transaction = session.beginTransaction();
        session.update(cart);
        updatedCart = session.find(Cart.class, cart.getId());
        transaction.commit();

        return updatedCart;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result;
        session.beginTransaction();
        Cart cart = session.find(Cart.class, id);
        if (cart != null) {
            session.delete(cart);
            result = (null == session.find(Cart.class, id));
        } else {
            result = false;
        }
        session.getTransaction().commit();

        return result;
    }
}
