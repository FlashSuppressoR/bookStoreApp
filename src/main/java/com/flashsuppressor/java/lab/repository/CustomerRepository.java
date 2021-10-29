package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface CustomerRepository {

    Customer findByEmail(String email) throws SQLException;
    Customer findById(int id) throws SQLException;
    List<Customer> findAll() throws SQLException;
    Customer update(Customer customer) throws SQLException;
    boolean deleteById(int id) throws SQLException;
    Customer add(Customer customer) throws SQLException;
}
