package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer findByEmail(String email);

    Customer findById(int id);

    List<Customer> findAll();

    void create(Customer customer);

    Customer update(Customer customer);

    boolean deleteById(int id);
}
