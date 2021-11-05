package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HibernatePurchaseRepository implements PurchaseRepository {
    private final Session session;
    private static final String FIND_ALL_PURCHASES_QUERY = "select p from Purchase p";

    @Autowired
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
    public void create(Purchase purchase) {
       session.save(purchase);
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
