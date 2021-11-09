package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;
import com.flashsuppressor.java.lab.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {

    CustomerDTO findByEmail(String email) throws ServiceException;

    CustomerDTO findById(int id) throws ServiceException;

    List<CustomerDTO> findAll() throws ServiceException, SQLException;

    void create(Customer customer) throws ServiceException;

    CustomerDTO update(Customer customer) throws ServiceException;

    boolean deleteById(int id) throws ServiceException;
}
