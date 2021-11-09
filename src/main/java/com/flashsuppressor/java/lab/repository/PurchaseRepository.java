package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.exception.RepositoryException;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseRepository {

    Purchase findById(int id) throws RepositoryException;

    List<Purchase> findAll();

    void create(Purchase purchase) throws RepositoryException;

    void createAll(List<Purchase> purchases);

    Purchase update(Purchase purchase) throws RepositoryException;

    boolean deleteById(int id) throws RepositoryException, SQLException;
}
