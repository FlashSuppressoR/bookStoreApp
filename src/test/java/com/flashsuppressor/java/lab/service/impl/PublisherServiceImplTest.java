package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.repository.PublisherRepository;
import com.flashsuppressor.java.lab.service.PublisherService;
import com.flashsuppressor.java.lab.service.TestServiceConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class PublisherServiceImplTest {
    @Autowired
    private PublisherRepository repository;
    @Autowired
    private PublisherService service;

    //TODO add tests
}
