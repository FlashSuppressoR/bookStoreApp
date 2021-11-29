package com.flashsuppressor.java.lab.repository.hibernate;

import com.flashsuppressor.java.lab.entity.Customer;

import java.util.List;

@Deprecated
public interface CustomerRepository {

    Customer findByEmail(String email);

    Customer findById(int id);

    List<Customer> findAll();

    Customer create(Customer customer);

    Customer update(Customer customer);

    boolean deleteById(int id);
}
