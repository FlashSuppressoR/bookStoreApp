package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class HibernatePurchaseRepository implements PurchaseRepository {
    private final Session session;
    private static final String FIND_ALL_PURCHASES_QUERY = "select p from Purchase p";

    public HibernatePurchaseRepository(Session session) {
        this.session = session;
    }

    @Override
    public List<Purchase> findAll() throws SQLException {
        List<Purchase> purchaseList;
        try {
            purchaseList = session.createQuery(FIND_ALL_PURCHASES_QUERY, Purchase.class).list();
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the purchases", ex);
        }
        return purchaseList;
    }

    @Override
    public Purchase findById(int id) throws SQLException {
        Purchase purchase;
        try {
            purchase = session.find(Purchase.class, id);
        } catch (Exception ex) {
            throw new SQLException("Can not find Purchase", ex);
        }
        return purchase;
    }

    @Override
    public Purchase add(Purchase purchase) throws SQLException {
        Purchase newPurchase;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer newPurchaseId = (Integer) session.save("Purchase", purchase);
            newPurchase = session.find(Purchase.class, newPurchaseId);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the Purchase", ex);
        }
        return newPurchase;
    }

    @Override
    public void addAll(List<Purchase> purchases) throws SQLException {
        try {
            for (Purchase purchase : purchases) {
                session.save(purchase);
            }
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the purchase", ex);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Purchase purchase = session.find(Purchase.class, id);

            if (purchase != null) {
                session.delete(purchase);
                result = (null == session.find(Purchase.class, id));
            } else {
                result = false;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Can not remove 'Purchase'", ex);
        }
        return result;
    }
}
