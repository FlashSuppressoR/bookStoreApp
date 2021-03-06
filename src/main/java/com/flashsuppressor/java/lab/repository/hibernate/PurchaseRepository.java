package com.flashsuppressor.java.lab.repository.hibernate;

import com.flashsuppressor.java.lab.entity.Purchase;

import java.util.List;

@Deprecated
public interface PurchaseRepository {

    Purchase findById(int id);

    List<Purchase> findAll();

    Purchase create(Purchase purchase);

    List<Purchase> createAll(List<Purchase> purchases);

    Purchase update(Purchase purchase);

    boolean deleteById(int id);
}
