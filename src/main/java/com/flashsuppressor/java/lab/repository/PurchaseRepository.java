package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Purchase;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseRepository {

    List<Purchase> findAll() throws SQLException;

    Purchase add(Purchase purchase) throws SQLException;

    Purchase findById(int id) throws SQLException;

    void addAll(List<Purchase> purchases) throws SQLException;

    boolean deleteById(int id) throws SQLException;
}
