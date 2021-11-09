package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RepositoryException.class)
public class HibernatePurchaseRepository implements PurchaseRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_ALL_PURCHASES_QUERY = "select p from Purchase p";

    @Autowired
    public HibernatePurchaseRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Purchase> findAll(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_PURCHASES_QUERY, Purchase.class).list();
    }

    @Override
    public Purchase findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return  session.find(Purchase.class, id);
    }

    @Override
    public void create(Purchase purchase) {
        Session session = sessionFactory.getCurrentSession();
        session.save(purchase);
    }

    @Override
    public void createAll(List<Purchase> purchases) {
        Session session = sessionFactory.getCurrentSession();
        for (Purchase purchase : purchases) {
            session.save(purchase);
        }
    }

    @Override
    public Purchase update(Purchase purchase) {
        Session session = sessionFactory.getCurrentSession();
        Purchase updatedPurchase;
        session.beginTransaction();
        session.update(purchase);
        updatedPurchase = session.find(Purchase.class, purchase.getId());
        session.getTransaction().commit();

        return updatedPurchase;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
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
