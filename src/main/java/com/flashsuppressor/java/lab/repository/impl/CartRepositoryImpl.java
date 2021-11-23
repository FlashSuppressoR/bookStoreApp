package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    @Autowired
    private final EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private static final String FIND_CARTS_QUERY = "select c from Cart c ";

    @Override
    public List<Cart> findAll() {
        Session session = getSession();
        return session.createQuery(FIND_CARTS_QUERY, Cart.class).list();
    }

    @Override
    public Cart findById(int id) {
        Session session = getSession();
        return session.find(Cart.class, id);
    }

    @Override
    public Cart create(Cart cart) {
        Session session = getSession();
        session.save(cart);
        return cart;
    }

    @Override
    public Cart update(Cart cart) {
        Session session = getSession();
        Cart updatedCart;
        Transaction transaction = session.beginTransaction();
        session.update(cart);
        updatedCart = session.find(Cart.class, cart);
        transaction.commit();

        return updatedCart;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = getSession();
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
