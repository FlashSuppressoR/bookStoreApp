package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.repository.CartRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HibernateCartRepository implements CartRepository {
    private final Session session;
    private static final String FIND_CARTS_QUERY = "select c from Cart c ";
    @Autowired
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
    public void create(Cart cart) {
        session.save(cart);
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
