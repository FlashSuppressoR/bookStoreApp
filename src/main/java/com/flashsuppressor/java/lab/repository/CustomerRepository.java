package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.exception.RepositoryException;

import java.sql.SQLException;
import java.util.List;

public interface CustomerRepository {

    Customer findByEmail(String email) throws RepositoryException;

    Customer findById(int id) throws RepositoryException;

    List<Customer> findAll() throws RepositoryException;

    void create(Customer customer) throws RepositoryException;

    Customer update(Customer customer) throws RepositoryException;

    boolean deleteById(int id) throws RepositoryException, SQLException;
}
