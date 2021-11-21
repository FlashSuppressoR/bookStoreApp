package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class PurchaseRepositoryImpl implements PurchaseRepository {

    @Autowired
    private final EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private static final String FIND_ALL_PURCHASES_QUERY = "select p from Purchase p";

    @Override
    public List<Purchase> findAll(){
        Session session = getSession();
        return session.createQuery(FIND_ALL_PURCHASES_QUERY, Purchase.class).list();
    }

    @Override
    public Purchase findById(int id) {
        Session session = getSession();
        return  session.find(Purchase.class, id);
    }

    @Override
    public void create(Purchase purchase) {
        Session session = getSession();
        session.save(purchase);
    }

    @Override
    public void createAll(List<Purchase> purchases) {
        Session session = getSession();
        for (Purchase purchase : purchases) {
            session.save(purchase);
        }
    }

    @Override
    public Purchase update(Purchase purchase) {
        Session session = getSession();
        Purchase updatedPurchase;
        session.beginTransaction();
        session.update(purchase);
        updatedPurchase = session.find(Purchase.class, purchase);
        session.getTransaction().commit();

        return updatedPurchase;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = getSession();
        boolean result;
        session.beginTransaction();
        Purchase purchase = session.find(Purchase.class, id);

        if (purchase != null) {
            session.delete(purchase);
            result = (null == session.find(Purchase.class, id));
        } else {
            result = false;
        }
        session.getTransaction().commit();

        return result;
    }
}
