package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Purchase;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseRepository {

    Purchase findById(int id) throws SQLException;

    List<Purchase> findAll();

    Purchase create(Purchase purchase) throws SQLException;

    void createAll(List<Purchase> purchases);

    Purchase update(Purchase purchase) throws SQLException;

    boolean deleteById(int id) throws SQLException;
}
