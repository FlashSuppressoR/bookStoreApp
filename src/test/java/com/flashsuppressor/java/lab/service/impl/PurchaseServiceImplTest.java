package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import com.flashsuppressor.java.lab.service.PurchaseService;
import com.flashsuppressor.java.lab.service.TestServiceConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class PurchaseServiceImplTest {

    @Autowired
    private PurchaseRepository repository;
    @Autowired
    private PurchaseService service;

    //TODO add tests
}
