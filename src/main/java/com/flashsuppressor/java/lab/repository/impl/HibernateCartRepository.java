package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.repository.CartRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class HibernateCartRepository implements CartRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_CARTS_QUERY = "select c from Cart c ";
    @Autowired
    public HibernateCartRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Cart> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_CARTS_QUERY, Cart.class).list();
    }

    @Override
    public Cart findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Cart.class, id);
    }

    @Override
    public void create(Cart cart) {
        Session session = sessionFactory.getCurrentSession();
        session.save(cart);
    }

    @Override
    public Cart update(Cart cart) {
        Session session = sessionFactory.getCurrentSession();
        Cart updatedCart;
        Transaction transaction = session.beginTransaction();
        session.update(cart);
        updatedCart = session.find(Cart.class, cart.getId());
        transaction.commit();

        return updatedCart;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
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
