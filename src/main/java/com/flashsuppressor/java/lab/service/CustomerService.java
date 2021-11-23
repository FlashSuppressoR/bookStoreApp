package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO findByEmail(String email);

    CustomerDTO findById(int id);

    List<CustomerDTO> findAll();

    CustomerDTO create(CustomerDTO customerDTO);

    CustomerDTO update(CustomerDTO customerDTO);

    boolean deleteById(int id);
}
