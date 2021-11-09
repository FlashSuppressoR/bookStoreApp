package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import com.flashsuppressor.java.lab.service.TestServiceConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class AuthorServiceImplTest {

    @Autowired
    private AuthorRepository repository;
    @Autowired
    private AuthorService service;

    //TODO add tests
}
