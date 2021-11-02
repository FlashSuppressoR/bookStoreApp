package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;
import org.hibernate.Session;

import java.util.List;

public class HibernatePurchaseRepository implements PurchaseRepository {
    private final Session session;
    private static final String FIND_ALL_PURCHASES_QUERY = "select p from Purchase p";

    public HibernatePurchaseRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Purchase> findAll(){

        return session.createQuery(FIND_ALL_PURCHASES_QUERY, Purchase.class).list();
    }

    @Override
    public Purchase findById(int id) {

        return  session.find(Purchase.class, id);
    }

    @Override
    public Purchase create(Purchase purchase) {
        Purchase newPurchase;
       session.beginTransaction();
       Integer newPurchaseId = (Integer) session.save(purchase);
       newPurchase = session.find(Purchase.class, newPurchaseId);
       session.getTransaction().commit();

        return newPurchase;
    }

    @Override
    public void createAll(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            session.save(purchase);
        }
    }

    @Override
    public Purchase update(Purchase purchase) {
        Purchase updatedPurchase;
        session.beginTransaction();
        session.update(purchase);
        updatedPurchase = session.find(Purchase.class, purchase.getId());
        session.getTransaction().commit();

        return updatedPurchase;
    }

    @Override
    public boolean deleteById(int id) {
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
