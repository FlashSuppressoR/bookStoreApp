package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.service.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerDTO findByEmail(String email);

    CustomerDTO findById(int id);

    Page<CustomerDTO> findAll(Pageable pageable);

    CustomerDTO create(CustomerDTO customerDTO);

    CustomerDTO update(CustomerDTO customerDTO);

    boolean deleteById(int id);
}
