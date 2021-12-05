package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.repository.hibernate.CartRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@AllArgsConstructor
@Deprecated
public class CartRepositoryImpl implements CartRepository {
    private static final String FIND_CARTS_QUERY = "select c from Cart c ";

    private final EntityManager entityManager;

    @Override
    public List<Cart> findAll() {

        return entityManager.createQuery(FIND_CARTS_QUERY, Cart.class).getResultList();
    }

    @Override
    public Cart findById(int id) {

        return entityManager.find(Cart.class, id);
    }

    @Override
    public Cart create(Cart cart) {
        Session session = entityManager.unwrap(Session.class);
        Integer newCartId = (Integer) session.save("Cart", cart);

        return session.find(Cart.class, newCartId);
    }

    @Override
    public Cart update(Cart cart) {
        Session session = entityManager.unwrap(Session.class);
        Cart updatedCart;
        Transaction transaction = session.beginTransaction();
        session.update(cart);
        updatedCart = session.find(Cart.class, cart);
        transaction.commit();

        return updatedCart;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
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
