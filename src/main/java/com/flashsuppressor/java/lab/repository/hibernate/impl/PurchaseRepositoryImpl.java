package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.repository.hibernate.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Deprecated
public class PurchaseRepositoryImpl implements PurchaseRepository {
    private static final String FIND_ALL_PURCHASES_QUERY = "select p from Purchase p";

    private final EntityManager entityManager;

    @Override
    public List<Purchase> findAll() {

        return entityManager.createQuery(FIND_ALL_PURCHASES_QUERY, Purchase.class).getResultList();
    }

    @Override
    public Purchase findById(int id) {

        return entityManager.find(Purchase.class, id);
    }

    @Override
    public Purchase create(Purchase purchase) {
        Session session = entityManager.unwrap(Session.class);
        Integer newPurchaseId = (Integer) session.save("Purchase", purchase);

        return session.find(Purchase.class, newPurchaseId);
    }

    @Override
    public List<Purchase> createAll(List<Purchase> purchases) {
        List<Purchase> newList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        for (Purchase purchase : purchases) {
            Integer newPurchaseId = (Integer) session.save("Purchase", purchase);
            newList.add(session.find(Purchase.class, newPurchaseId));
        }

        return newList;
    }

    @Override
    public Purchase update(Purchase purchase) {
        Session session = entityManager.unwrap(Session.class);
        Purchase updatedPurchase;
        session.beginTransaction();
        session.update(purchase);
        updatedPurchase = session.find(Purchase.class, purchase);
        session.getTransaction().commit();

        return updatedPurchase;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
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
