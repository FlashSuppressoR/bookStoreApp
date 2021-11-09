package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.repository.CustomerRepository;
import com.flashsuppressor.java.lab.service.CustomerService;
import com.flashsuppressor.java.lab.service.TestServiceConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class CustomerServiceImplTest {

    @Autowired
    private CustomerRepository repository;
    @Autowired
    private CustomerService service;

    //TODO add tests
}
